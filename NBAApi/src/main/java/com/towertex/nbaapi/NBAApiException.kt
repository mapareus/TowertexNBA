package com.towertex.nbaapi

class NBAApiException (
    val httpCode: Int = 0,
    val nbaError: NBAApiErrorType? = null,
    val serviceUrl: String = "",
) : RuntimeException()