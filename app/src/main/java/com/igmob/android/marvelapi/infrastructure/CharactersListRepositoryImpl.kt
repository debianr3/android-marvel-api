package com.igmob.android.marvelapi.infrastructure

import com.igmob.android.marvelapi.domain.core.Character
import com.igmob.android.marvelapi.domain.core.CharactersListRepository


class CharactersListRepositoryImpl(
    private val charactersListService: CharactersListService
) : CharactersListRepository {

    override suspend fun getCharacters(): List<Character> {
        TODO("Implement me!")
    }
}