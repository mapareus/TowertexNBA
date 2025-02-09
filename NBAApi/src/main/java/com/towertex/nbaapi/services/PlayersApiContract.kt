package com.towertex.nbaapi.services

import com.towertex.nbaapi.model.PlayersResponse
import retrofit2.Call

interface PlayersApiContract {
    fun getAllPlayers(page: Int): Call<PlayersResponse>
}