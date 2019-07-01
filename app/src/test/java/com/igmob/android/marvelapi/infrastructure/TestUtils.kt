package com.igmob.android.marvelapi.infrastructure

import com.squareup.moshi.Moshi

fun loadResource(path: String): String {
    return String::class.java.getResource("/$path")!!.readText()
}

fun <T> fromJson(clazz: Class<T>, path: String): T? {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(clazz)
    return jsonAdapter.fromJson(loadResource(path))
}