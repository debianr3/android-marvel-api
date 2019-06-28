package com.igmob.android.marvelapi.infrastructure

import com.igmob.android.marvelapi.domain.core.Character
import retrofit2.Response
import retrofit2.http.GET

interface CharactersListService {
    @GET
    suspend fun getCharactersList(): Response<List<Character>>
}