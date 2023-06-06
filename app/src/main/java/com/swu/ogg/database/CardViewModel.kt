package com.swu.ogg.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CardViewModel(private val repository: OggRepository) : ViewModel() {

    val projectList : LiveData<List<UserProject>> = repository.projectList.asLiveData()

    fun insert(userProject: UserProject) = viewModelScope.launch{
        repository.insert(userProject)
    }
}

class CardViewModelFactory(private val repository: OggRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CardViewModel::class.java)) {

            @Suppress("UNCKECKED_CAST")
            return CardViewModel(repository) as T
        }

        throw  java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}