package com.igmob.android.marvelapi.application.ui.characterslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igmob.android.marvelapi.domain.core.Character
import com.igmob.android.marvelapi.domain.characterslist.CharactersListRepository
import com.igmob.android.marvelapi.domain.core.Resource
import com.igmob.android.marvelapi.domain.usecases.GetCharactersListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CharactersListViewModel(private val repository: CharactersListRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val successResourceState = MutableLiveData<Resource.Success<List<Character>>>()
    val errorResourceState = MutableLiveData<Resource.Error>()

    fun fetchCharactersList() {
        viewModelScope.launch {
            val characters = GetCharactersListUseCase(repository).execute()
            when (characters) {
                is Resource.Success<List<Character>> -> successResourceState.postValue(characters)
                is Resource.Error -> errorResourceState.postValue(characters)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(
        private val charactersListRepository: CharactersListRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersListViewModel(charactersListRepository) as T
        }
    }
}