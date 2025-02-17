package com.towertex.nba.compose

import androidx.compose.runtime.Composable
import com.towertex.nba.navigation.NBANavHost
import com.towertex.nba.theme.TowertexNBATheme
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun NBAApp() {
    TowertexNBATheme {
        KoinAndroidContext {
            NBANavHost()
        }
    }
}