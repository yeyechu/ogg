package com.swu.ogg.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

// 저장소와 UI 간 통신 담당, UI에 데이터를 제공하고 구성 변경에도 유지
// Fragment간 데이터 공유도 가능

// WordRepository를 매개변수로 하여 ViewModel을 확장하는 클래스
// 단어 목록을 cache하는 공개 LiveData 멤버변수 추가
// 저장소의 allRecords Flow를 사용하여 LiveData 초기화 후 asLiveData().를 호출하여 Flow를 LiveData로 변환
// 저장소의 insert() 메서드를 호출하는 래퍼 insert() 생성 -> insert() 구현이 UI에서 캡슐화
// 새 코루틴을 실행하여 정지 함수인 Repository의 insert 호출, ViewModel에는 수명주기 기반의 코루틴 범위인 viewModelScope 사용

// ViewModel을 만든 후 WordViewModel을 만드는 데 필요한 종속 항목(WordRepository)을 매개변수로 나져오는 ViewModelProvider.Factory 구현
// viewModels와 ViewModelProvider.Factory를 사용하여 ViewModel의 수명 주기 처리
// 구성 변경에도 유지되고 Activity가 재생성 돼도 항상 WordViewModel 클래스의 올바른 인스턴스를 가져오게 됨
// ViewModel보다 수명주기가 짧은 Context 참조는 유지하지 말것 -> Activity, Fragment, View
// -> 메모리 누수를 발생시킴

class RecordViewModel(private val repository : RecordRepository) : ViewModel() {

    // LiveDate와 allRecords가 반환하는 caching을 사용하면 좋은점 :
    // - 데이터에 observer를 두고, 실제 데이터가 변하는 시점에만 UI를 업데이트 할 수 있음
    // - Repository가 ViewModel을 통해 UI로부터 완전히 분리됨

    val allRecords : LiveData<List<Record>> = repository.allRecords.asLiveData()

    fun insert(record : Record) = viewModelScope.launch {
        repository.insert(record)
    }
}

class RecordViewModelFactory(private val repository: RecordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return  RecordViewModel(repository) as T
        }
        throw  java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}

/*
class MainViewModel(
    application: Application
) : ViewModel() {

    private val repository: ExpenseRepository

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    fun insertExpense(expense: Expense) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertExpense(expense)
        }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }

    }
}
 */