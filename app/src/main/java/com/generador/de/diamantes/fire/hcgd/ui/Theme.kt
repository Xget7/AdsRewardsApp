package com.app.listaActividades.ui

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.generador.de.diamantes.fire.hcgd.ui.Typography
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val ColorPalette = lightColors(
    primary = Color(0xFF5C95CA),
    primaryVariant = Color(0xFF2690FF),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF2E2E2E),
    surface = Color(0xFFDEF2FF),
    onSurface = Color(0xFF5461BE),
    onSecondary = Color(0xFF131313)
)

//implementar
private val DarkrPalette = darkColors(
    primary = Color(0xFF5C95CA),
    primaryVariant = Color(0xFF2690FF),
    background = Color(0xFFFCF3ED),
    onBackground = Color(0xFF2E2E2E),
    surface = Color(0xFFDEF2FF),
    onSurface = Color(0xFF5461BE),
    onSecondary = Color(0xFF131313)
)

@SuppressLint("SuspiciousIndentation")
@Composable
fun DiamantesFreeFireTheme(darkTheme: Boolean = false, content : @Composable() () -> Unit) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )
    MaterialTheme(
        colors = if (!darkTheme) ColorPalette else DarkrPalette ,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}