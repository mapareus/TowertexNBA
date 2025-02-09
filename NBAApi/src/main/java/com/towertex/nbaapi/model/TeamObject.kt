package com.towertex.nbaapi.model

import kotlinx.serialization.Serializable

@Serializable
data class TeamObject (
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    val full_name: String,
    val abbreviation: String,
)