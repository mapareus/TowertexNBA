package com.towertex.nbaapi

enum class NBAApiErrorType {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    BAD_REQUEST,
    NOT_FOUND,
    NOT_ACCEPTABLE,
    INTERNAL_SERVER_ERROR,
    SERVICE_UNAVAILABLE,
    UNKNOWN;

    companion object {
        fun fromHttpCode(httpCode: Int): NBAApiErrorType {
            return when (httpCode) {
                400 -> BAD_REQUEST
                401 -> UNAUTHORIZED
                404 -> NOT_FOUND
                406 -> NOT_ACCEPTABLE
                408 -> REQUEST_TIMEOUT
                409 -> CONFLICT
                413 -> PAYLOAD_TOO_LARGE
                429 -> TOO_MANY_REQUESTS
                500 -> INTERNAL_SERVER_ERROR
                503 -> SERVICE_UNAVAILABLE
                in 500..599 -> SERVER_ERROR
                else -> UNKNOWN
            }
        }
    }
}