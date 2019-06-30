package com.igmob.android.marvelapi.domain.core

sealed class Resource<out T : Any>(open val attributionText: String?) {

    data class Success<out T : Any>(
        override val attributionText: String?,
        val data: T?
    ) : Resource<T>(attributionText)

    data class Error(
        override val attributionText: String?,
        val statusCode: Int? = null,
        val message: String? = null
    ) : Resource<Nothing>(attributionText)
}