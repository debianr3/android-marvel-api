package com.igmob.android.marvelapi.domain.core

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)