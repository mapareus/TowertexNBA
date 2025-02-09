package com.towertex.nba.repository

interface GlideRepositoryContract {
    fun getGlideUrl(shortName: String): String
}