package com.towertex.nbaapi.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponse (
    val data: List<PlayerObject>,
    val meta: MetaObject,
)