package com.igmob.android.marvelapi.domain.usecases

import com.igmob.android.marvelapi.domain.core.CharactersListRepository

class GetCharactersListUseCase(
    private val repository: CharactersListRepository
) {
    suspend fun execute() = repository.getCharacters()

}