package com.towertex.nbaapi.services

import com.towertex.nbaapi.NBACall
import com.towertex.nbaapi.model.PlayersResponse
import retrofit2.Call
import retrofit2.Retrofit

class PlayersApi(
    retrofit: Retrofit,
    private val apiKey: String,
    private val perPage: Int
) : PlayersApiContract {
    private val services = retrofit.create(PlayersServices::class.java)

    override fun getAllPlayers(
        page: Int
    ): Call<PlayersResponse> = NBACall (
        services.getAllPlayers(
            apiKey,
            (page-1)*perPage,
            perPage
        )
    )
}