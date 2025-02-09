package com.towertex.nba.repository

import com.towertex.nba.R

class GlideRepository(private val res: ResourceRepositoryContract) : GlideRepositoryContract {
    override fun getGlideUrl(shortName: String): String = when (shortName) {
        "Celtics" -> "bos"
        "Nets" -> "bkn"
        "Knicks" -> "ny"
        "76ers" -> "phi"
        "Raptors" -> "tor"
        "Bulls" -> "chi"
        "Cavaliers" -> "cle"
        "Pistons" -> "det"
        "Pacers" -> "ind"
        "Bucks" -> "mil"
        "Hawks" -> "atl"
        "Hornets" -> "cha"
        "Heat" -> "mia"
        "Magic" -> "orl"
        "Wizards" -> "wsh"
        "Nuggets" -> "den"
        "Timberwolves" -> "min"
        "Thunder" -> "okc"
        "Trail Blazers" -> "por"
        "Jazz" -> "utah"
        "Warriors" -> "gs"
        "Clippers" -> "lac"
        "Lakers" -> "lal"
        "Suns" -> "phx"
        "Kings" -> "sac"
        "Mavericks" -> "dal"
        "Rockets" -> "hou"
        "Grizzlies" -> "mem"
        "Pelicans" -> "no"
        "Spurs" -> "sa"
        else -> ""
    }.let { res.getString(R.string.td_logo_url, it) }
}