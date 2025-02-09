package com.towertex.nbaapi.services

import com.towertex.nbaapi.model.PlayersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PlayersServices {
    @GET("v1/players")
    fun getAllPlayers(
        @Header("Authorization") authToken: String,
        @Query("cursor") cursor: Int,
        @Query("per_page") perPage: Int,
    ): Call<PlayersResponse>
}