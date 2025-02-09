package com.towertex.nbaapi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class NBAApiBuilder (buildBlock: NBAApiBuilder.() -> Unit) {
    private var endpoint: String = BASE_URL
    private var token: String = NBA_API_KEY
    private var perPage: Int = EXPECTED_PAGE_SIZE
    private var httpLoggingInterceptor: HttpLoggingInterceptor? = null

    init {
        buildBlock.invoke(this)
    }

    companion object {
        private const val BASE_URL = "https://api.balldontlie.io/"
        private const val NBA_API_KEY = "d9a551fe-f341-4e8a-84e7-2f436d87a2fb"
        private const val EXPECTED_PAGE_SIZE = 35
        private val defaultHttpLoggingInterceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        private val networkJson = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    private fun getHttpClient(
    ): OkHttpClient = OkHttpClient.Builder()
        .apply { httpLoggingInterceptor?.also { addNetworkInterceptor(it) } }
        .build()

    private fun buildRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(endpoint)
        .client(getHttpClient())
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType())) // should add it at last
        .build()

    fun setEndpoint(endpoint: String) = apply { this.endpoint = endpoint }

    fun setToken(token: String) = apply { this.token = token }

    fun setPerPage(perPage: Int) = apply { this.perPage = perPage }

    fun enableLogging() = apply { httpLoggingInterceptor = defaultHttpLoggingInterceptor }

    fun build(): NBAApi = NBAApi(buildRetrofit(), token, perPage)
}