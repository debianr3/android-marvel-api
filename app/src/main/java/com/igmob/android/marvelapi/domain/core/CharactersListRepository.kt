package com.igmob.android.marvelapi.domain.core

interface CharactersListRepository {
    suspend fun getCharacters(): List<Character>
}