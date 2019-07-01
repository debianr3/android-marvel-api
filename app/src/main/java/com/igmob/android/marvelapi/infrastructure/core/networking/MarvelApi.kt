package com.igmob.android.marvelapi.infrastructure.core.networking

import android.util.Log
import com.igmob.android.marvelapi.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MarvelApi {
    private const val TAG: String = "MarvelApi"

    @JvmStatic
    fun getHash(httpUrlBuilder: HttpUrl.Builder): HttpUrl {
        // hash = md5Sum(timestamp + private key + public key)
        val ts = System.currentTimeMillis().toString()
        val privateKey = BuildConfig.MARVEL_API_PRIVATE_KEY
        val publicKey = BuildConfig.MARVEL_API_PUBLIC_KEY
        val hash = md5Sum(ts + privateKey + publicKey)

        val url = httpUrlBuilder
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .addQueryParameter("ts", ts)
            .build()

        return url
    }

    @Suppress("MagicNumber")
    private fun md5Sum(encTarget: String): String {
        var mdEnc: MessageDigest? = null
        try {
            mdEnc = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Error getting algorithm", e)
        }
        mdEnc!!.update(encTarget.toByteArray(), 0, encTarget.length)
        var md5 = BigInteger(1, mdEnc.digest()).toString(16)
        while (md5.length < 32) {
            md5 = "0$md5"
        }
        return md5
    }
}

class MarvelApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val hash = MarvelApi.getHash(originalHttpUrl.newBuilder())

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(hash)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}