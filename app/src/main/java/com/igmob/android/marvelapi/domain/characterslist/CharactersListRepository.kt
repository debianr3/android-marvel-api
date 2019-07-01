package com.igmob.android.marvelapi.domain.characterslist

import com.igmob.android.marvelapi.domain.core.Character
import com.igmob.android.marvelapi.domain.core.Resource

interface CharactersListRepository {
    suspend fun getCharacters(): Resource<List<Character>>
}

