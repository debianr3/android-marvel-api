package com.igmob.android.marvelapi.domain.core

// https://developer.marvel.com/documentation/images
data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun imageSquareUri() = "$path/landscape_incredible.$extension"
}