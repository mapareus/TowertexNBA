package com.towertex.nba.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    companion object {
        private const val PLAYER_DETAIL = "playerDetail/"
        const val PLAYER_ID = "playerId"
        private const val TEAM_DETAIL = "teamDetail/"
        const val TEAM_ID = "teamId"
    }

    data object Home : Screen("home")

    data object PlayerDetail : Screen(
        route = "$PLAYER_DETAIL{$PLAYER_ID}",
        navArguments = listOf(navArgument(PLAYER_ID) {
            type = NavType.IntType
        })
    ) {
        fun createRoute(playerId: Int) = "$PLAYER_DETAIL${playerId}"
    }

    data object TeamDetail : Screen(
        route = "$TEAM_DETAIL{$TEAM_ID}",
        navArguments = listOf(navArgument(TEAM_ID) {
            type = NavType.IntType
        })
    ) {
        fun createRoute(teamId: Int) = "$TEAM_DETAIL${teamId}"
    }
}