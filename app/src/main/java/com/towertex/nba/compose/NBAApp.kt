package com.towertex.nba.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.towertex.nba.navigation.NBANavHost
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun NBAApp() {
    MaterialTheme {
        KoinAndroidContext {
            NBANavHost()
        }
    }
}