package com.igmob.android.marvelapi.infrastructure.characterslist

import com.igmob.android.marvelapi.domain.core.Resource
import com.igmob.android.marvelapi.infrastructure.fromJson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.test.AutoCloseKoinTest
import retrofit2.Response
import java.util.concurrent.TimeoutException


internal class CharactersListRepositoryImplTest : AutoCloseKoinTest() {

    companion object {
        @JvmStatic
        private fun mockServiceSuccess(): CharactersListService {
            val charactersListResponse = fromJson(CharactersListResponse::class.java, "characters_list.json")
            val charactersListService: CharactersListService = mockk()

            coEvery {
                charactersListService.getCharactersList(any())
            } returns Response.success(charactersListResponse)

            return charactersListService
        }

        @JvmStatic
        private fun mockServiceError(): CharactersListService {
            val charactersListService: CharactersListService = mockk()

            coEvery {
                charactersListService.getCharactersList(any())
            } returns Response.error(500, ResponseBody.create(null, "Service unavailable"))

            return charactersListService
        }

        @JvmStatic
        private fun mockServiceFailure(): CharactersListService {
            val charactersListService: CharactersListService = mockk()

            coEvery {
                charactersListService.getCharactersList(any())
            } throws TimeoutException()

            return charactersListService
        }
    }

    @Test
    fun testGetCharactersSuccess() {
        val service = mockServiceSuccess()
        runBlocking {
            val resource = CharactersListRepositoryImpl(
                service
            ).getCharacters()

            if (resource is Resource.Success) {
                Assertions.assertEquals(1011334L, resource.data!![0].id)
            } else {
                Assertions.fail("Resource is ${resource.javaClass.simpleName}")
            }
        }
    }

    @Test
    fun testGetCharactersError() {
        val service = mockServiceError()
        runBlocking {
            val resource = CharactersListRepositoryImpl(
                service
            ).getCharacters()

            if (resource is Resource.Error) {
                Assertions.assertEquals(500, resource.statusCode)
                Assertions.assertEquals("Service unavailable", resource.message)
            } else {
                Assertions.fail("Resource is ${resource.javaClass.simpleName}")
            }
        }
    }

    @Test
    fun testGetCharactersFailure() {
        val service = mockServiceFailure()
        runBlocking {
            val resource = CharactersListRepositoryImpl(
                service
            ).getCharacters()
            Assertions.assertTrue(resource is Resource.Error, "Resource is ${resource.javaClass.simpleName}")
        }
    }
}