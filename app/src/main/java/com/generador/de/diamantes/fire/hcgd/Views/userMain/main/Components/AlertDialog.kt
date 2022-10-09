package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.Components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext

@Composable
fun AlertDialogComponent(
    onClickRegister : () -> Unit
) {
    // below line is use to get
    // the context for our app.
    val context = LocalContext.current

    // below line is use to set our state
    // of dialog box to open as true.
    val openDialog = remember { mutableStateOf(true) }

    // below line is to check if the
    // dialog box is open or not.
    if (openDialog.value) {
        // below line is use to
        // display a alert dialog.
        AlertDialog(
            // on dialog dismiss we are setting
            // our dialog value to false.
            shape = RectangleShape,
            onDismissRequest = { openDialog.value = false },

            // below line is use to display title of our dialog
            // box and we are setting text color to white.
            title = { Text(text = "Verifica tu telefono", color = Color.White) },

            // below line is use to display
            // description to our alert dialog.
            text = { Text("Aun no tienes registrado tu numero de telefono!", color = Color.White) },

            // in below line we are displaying
            // our confirm button.
            confirmButton = {
                // below line we are adding on click
                // listener for our confirm button.
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onClickRegister()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = CircleShape

                ) {
                    // in this line we are adding
                    // text for our confirm button.
                    Text("Registrar", color = Color.Black)
                }
            },
            // in below line we are displaying
            // our dismiss button.
            dismissButton = {
                // in below line we are displaying
                // our text button
                TextButton(
                    // adding on click listener for this button
                    onClick = {
                        openDialog.value = false
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC73636))
                ) {
                    // adding text to our button.
                    Text("Cancelar", color = Color.White)
                }
            },
            // below line is use to add background color to our alert dialog
            backgroundColor = Color(0xFF097CD5),

            // below line is use to add content color for our alert dialog.
            contentColor = Color.White
        )
    }
}