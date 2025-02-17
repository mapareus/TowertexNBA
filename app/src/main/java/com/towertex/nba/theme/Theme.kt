package com.towertex.nba.theme

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun TowertexNBATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = selectScheme(darkTheme, dynamicColor),
        typography = NBATypography,
        shapes = NBAShapes,
        content = content
    )
}

@Composable
fun selectScheme(isDark: Boolean, isDynamic: Boolean): ColorScheme = when {
    Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> if(isDark) NBADarkScheme else NBALightScheme
    isDynamic -> if (isDark) dynamicDarkColorScheme(LocalContext.current) else dynamicLightColorScheme(LocalContext.current)
    else -> selectSchemeForContrast(isDark)
}

@Composable
fun selectSchemeForContrast(isDark: Boolean): ColorScheme = when(getContrastLevel()) {
    in 0.34f..0.66f -> if (isDark) NBAMediumContrastDarkScheme else NBAMediumContrastLightScheme
    in 0.67f..1.0f -> if (isDark) NBAHighContrastDarkScheme else NBAHighContrastLightScheme
    else -> if (isDark) NBADarkScheme else NBALightScheme
}

@SuppressLint("NewApi")
@Composable
fun getContrastLevel(): Float {
    if(LocalInspectionMode.current) return 0.0f //is in Preview
    val context = LocalContext.current
    val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    return uiModeManager.contrast
}