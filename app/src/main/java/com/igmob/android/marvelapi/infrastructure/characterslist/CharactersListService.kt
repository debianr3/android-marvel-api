package com.igmob.android.marvelapi.infrastructure.characterslist

import com.igmob.android.marvelapi.domain.core.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersListService {
    @GET("v1/public/characters")
    suspend fun getCharactersList(@Query("limit") limit: Int = 30): Response<CharactersListResponse>
}

data class CharactersListResponse(
    val attributionText: String,
    val data: CharactersListResponseData
)

data class CharactersListResponseData(
    val results: List<Character>
)