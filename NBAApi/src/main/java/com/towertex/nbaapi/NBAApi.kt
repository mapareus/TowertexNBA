package com.towertex.nbaapi

import com.towertex.nbaapi.services.PlayersApiContract
import com.towertex.nbaapi.services.PlayersApi
import retrofit2.Retrofit

//server api is typically divided into groups of services, here each group is implemented in separate delegate
class NBAApi internal constructor(retrofit: Retrofit, apiKey: String, perPage: Int) :
    PlayersApiContract by PlayersApi(retrofit, apiKey, perPage)