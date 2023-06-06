package com.swu.ogg.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

//class CardViewModel(private val repository: OggRepository) : ViewModel() {
//
//    // LiveDate와 allWords가 반환하는 caching을 사용하면 좋은점 :
//    // - 데이터에 observer를 두고, 실제 데이터가 변하는 시점에만 UI를 업데이트 할 수 있음
//    // - Repository가 ViewModel을 통해 UI로부터 완전히 분리됨
//
//    val projectList : LiveData<List<UserProject>> = repository.projectList.asLiveData()
//
//    fun insert(userProject: UserProject) = viewModelScope.launch{
//        repository.insert(userProject)
//
//    }
//}
//
//class CardViewModelFactory(private val repository: OggRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(CardViewModel::class.java)) {
//
//            @Suppress("UNCKECKED_CAST")
//            return CardViewModel(repository) as T
//        }
//
//        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
//    }
//}