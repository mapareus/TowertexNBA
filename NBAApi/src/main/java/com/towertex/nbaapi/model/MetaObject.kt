package com.towertex.nbaapi.model

import kotlinx.serialization.Serializable

@Serializable
data class MetaObject (
    val next_cursor: Int,
    val per_page: Int,
)