package com.generador.de.diamantes.fire.hcgd.Views.auth.phoneVerification

import android.annotation.SuppressLint
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.generador.de.diamantes.fire.hcgd.R
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.talhafaki.composablesweettoast.theme.Purple500


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PhoneVerifyScreen(
    viewModel: PhoneVerifyViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    var otpVal = remember { mutableStateOf("") }
    val customView = remember { LottieAnimationView(context) }
    val coroutine = rememberCoroutineScope()


    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Purple500),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Autenticacion",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            if (viewModel.state.value.isLoading == false && viewModel.state.value.isSucessSendedMsg == false) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    //Add Lottie file

                    AndroidView(
                        {
                            customView
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ) { view ->
                        with(view) {
                            setAnimation(R.raw.phone_number_verify)
                            playAnimation()
                            repeatCount = LottieDrawable.INFINITE
                            foregroundGravity = Gravity.CENTER
                        }
                    }


                    Spacer(modifier = Modifier.height(50.dp))

                    OutlinedTextField(
                        value = viewModel.phone.value,
                        onValueChange = { viewModel.phone.value = it },
                        label = { Text(text = "Numero de telefono") },
                        placeholder = { Text(text = "Incluye tu codigo pais") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Phone,
                                contentDescription = "Numero de telefono"
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.startPhoneNumberVerification(context)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(45.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Purple500),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ),
                        elevation = ButtonDefaults.elevation(4.dp)

                    ) {
                        Text(text = "Enviar Codigo", fontSize = 15.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(40.dp))




                    if (viewModel.state.value.errorMsg != null) {
                        Snackbar(
                            modifier = Modifier.padding(
                                top = 10.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            backgroundColor = Color(0xFFFFFFFF),
                            elevation = 4.dp,
                            actionOnNewLine = false,
                            action = {
                                TextButton(
                                    onClick = {
                                        viewModel.dismiss()
                                    },
                                ) {
                                    Text(text = "Okey", color = Color.Black)
                                }
                            }
                        ) {
                            Text(text = viewModel.state.value.errorMsg!!, color = Color.Black)
                        }
                    }
                }

            } else if (viewModel.state.value.isSucessVerified == true) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AndroidView(
                        {
                            customView
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ) { view ->
                        with(view) {
                            setAnimation(R.raw.sucess)
                            playAnimation()
                            repeatCount = 0
                            foregroundGravity = Gravity.CENTER
                        }
                    }
                    Text(text = "Verificado Correctamente!")

                    TextButton(onClick = {
                        navController.navigateUp()
                        navController.navigate(Screens.UserMainScreen.route)
                    }) {
                        Text(text = "Volver a la pantalla principal", fontSize = 20.sp)
                    }


                }

            } else if (viewModel.state.value.isLoading == true) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (viewModel.state.value.isSucessSendedMsg == true) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Ingresa aqui el codigo de verificacion enviado a ${viewModel.phone.value}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = otpVal.value,
                        onValueChange = { otpVal.value = it },
                        label = { Text(text = "Codigo") },
                        placeholder = { Text(text = "Codigo") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Message,
                                contentDescription = "Codigo"
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            if (otpVal != null) {
                                viewModel.verifyPhoneNumberWithCode(
                                    viewModel.phoneVerificationId.value,
                                    otpVal.value
                                ) // Here pass the Otp value only
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(45.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Purple500),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ),

                        ) {
                        Text(
                            text = "Verificar Codigo",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                    if (viewModel.state.value.errorMsg != null) {
                        Snackbar(
                            modifier = Modifier.padding(
                                top = 10.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            backgroundColor = Color(0xFFFFFFFF),
                            elevation = 4.dp,
                            actionOnNewLine = false,
                            action = {
                                TextButton(
                                    onClick = {
                                        viewModel.dismiss()
                                    },
                                ) {
                                    Text(text = "Okey", color = Color.Black)
                                }
                            }
                        ) {
                            Text(text = viewModel.state.value.errorMsg!!, color = Color.Black)
                        }
                    }
                }


            }


        }
    }

}
