package com.towertex.nba.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towertex.nba.compose.PlayerDetail
import com.towertex.nba.compose.PlayerList
import com.towertex.nba.compose.TeamDetail

@Composable
fun NBANavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            PlayerList(
                onClick = { navController.navigate(Screen.PlayerDetail.createRoute(playerId = it)) }
            )
        }
        composable(
            route = Screen.PlayerDetail.route,
            arguments = Screen.PlayerDetail.navArguments
        ) { stack ->
            PlayerDetail(
                playerId = stack.arguments?.getInt(Screen.PLAYER_ID) ?: 0,
                onBack = { navController.navigateUp() },
                onClick = { navController.navigate(Screen.TeamDetail.createRoute(teamId = it)) }
            )
        }
        composable(
            route = Screen.TeamDetail.route,
            arguments = Screen.TeamDetail.navArguments
        ) {
            TeamDetail(
                teamId = it.arguments?.getInt(Screen.TEAM_ID) ?: 0,
                onBack = { navController.navigateUp() }
            )
        }
    }
}