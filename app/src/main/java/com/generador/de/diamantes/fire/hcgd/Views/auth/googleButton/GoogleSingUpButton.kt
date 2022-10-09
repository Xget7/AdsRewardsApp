package com.generador.de.diamantes.fire.hcgd.Views.auth.googleButton

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.generador.de.diamantes.fire.hcgd.R
import com.app.listaActividades.ui.Shapes

@ExperimentalMaterialApi
@Composable
fun GoogleSignInButtonUi(
    text : String = "",
    loadingText: String = "" ,
    onClicked:() -> Unit
)

{

    var clicked by remember { mutableStateOf(false)}
    Surface(
        onClick = {clicked = !clicked},
        shape = Shapes.medium,
        border = BorderStroke(width = 1.dp,color = Color.LightGray),
        color = Color.White,
        elevation = 4.dp
    ) {
        Row (modifier = Modifier
            .padding(
                start = 12.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
            .animateContentSize(
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            ),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center){
            Icon(painter = painterResource(id = R.drawable.ic_google_icon), contentDescription = "Google sign button", tint = Color.Unspecified)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) loadingText else text)

            if (clicked){
                Spacer(modifier = Modifier.width(16.dp))
                onClicked()
            }

        }
    }



}

@ExperimentalMaterialApi
@Composable
@Preview
fun GoogleButtonPreview(){
    GoogleSignInButtonUi(
        text = "Sign Up With Google",
        loadingText = "Signing In....",
        onClicked = {}
    )
}