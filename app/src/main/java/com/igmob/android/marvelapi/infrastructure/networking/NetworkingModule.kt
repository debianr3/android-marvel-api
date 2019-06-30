package com.igmob.android.marvelapi.infrastructure.networking

import com.igmob.android.marvelapi.BuildConfig
import com.igmob.android.marvelapi.domain.core.CharactersListRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val NetworkingModule = module {
    single<CharactersListRepository> { CharactersListRepositoryImpl(get()) }

    factory {
        provideRetrofit()
    }

    factory {
        provideCharactersListService(get())
    }
}

private fun provideRetrofit(): Retrofit {
    val client = OkHttpClient.Builder().addInterceptor(MarvelApiKeyInterceptor()).build()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun provideCharactersListService(retrofit: Retrofit): CharactersListService =
    retrofit.create(CharactersListService::class.java)
