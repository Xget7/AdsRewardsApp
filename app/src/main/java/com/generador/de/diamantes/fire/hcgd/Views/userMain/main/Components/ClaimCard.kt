package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClaimCard(
    amount : String,
    onClick : () -> Unit,
) {
    Card(
        Modifier
            .height(100.dp)
            .width(70.dp)
            .padding(16.dp)
            .clickable {
                       onClick()
            },
        shape = RoundedCornerShape(10.dp),
        backgroundColor =  MaterialTheme.colors.primaryVariant
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = amount,
                fontSize = 40.sp,
                color = MaterialTheme.colors.surface,
                fontWeight = FontWeight.Medium
            )
            Image(
                painter = painterResource(id = com.generador.de.diamantes.fire.hcgd.R.drawable.dollarsymbol),
                contentDescription = "Dolar ",
                modifier = Modifier.width(40.dp).height(40.dp)
            )
        }

    }

}