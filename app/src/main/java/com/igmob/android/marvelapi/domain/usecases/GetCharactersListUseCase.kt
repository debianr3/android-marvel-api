package com.igmob.android.marvelapi.domain.usecases

import com.igmob.android.marvelapi.domain.characterslist.CharactersListRepository

class GetCharactersListUseCase(
    private val repository: CharactersListRepository
) {
    suspend fun execute() = repository.getCharacters()

}