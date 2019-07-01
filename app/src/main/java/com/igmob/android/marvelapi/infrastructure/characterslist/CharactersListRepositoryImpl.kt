package com.igmob.android.marvelapi.infrastructure.characterslist

import android.util.Log
import com.igmob.android.marvelapi.domain.core.Character
import com.igmob.android.marvelapi.domain.characterslist.CharactersListRepository
import com.igmob.android.marvelapi.domain.core.Resource


class CharactersListRepositoryImpl(
    private val service: CharactersListService
) : CharactersListRepository {

    companion object {
        private const val TAG: String = "CharactersListRepositoryImpl"
        private const val DEFAULT_CHARACTERS_LIMIT = 10
    }


    @Suppress("TooGenericExceptionCaught")
    override suspend fun getCharacters(): Resource<List<Character>> {
        var resource: Resource<List<Character>>
        val response = try {
            service.getCharactersList(DEFAULT_CHARACTERS_LIMIT)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching characters", e)
            null
        }

        if (response != null && response.isSuccessful) {
            resource =
                Resource.Success(response.body()?.attributionText, response.body()?.data?.results)
        } else {
            resource =
                Resource.Error(response?.body()?.attributionText, response?.code(), response?.errorBody()?.string())
        }

        return resource
    }
}