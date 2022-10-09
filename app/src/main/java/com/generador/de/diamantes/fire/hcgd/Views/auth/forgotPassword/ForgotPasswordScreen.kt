package com.generador.de.diamantes.fire.hcgd.Views.auth.forgotPassword

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.generador.de.diamantes.fire.hcgd.R
import com.generador.de.diamantes.fire.hcgd.util.Screens
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalCoroutinesApi
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = viewModel(),
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val customView = remember { LottieAnimationView(context) }
    val kc = LocalSoftwareKeyboardController.current


    Box(
        modifier = Modifier
            .fillMaxSize()

            .background(MaterialTheme.colors.background)
    ) {
        IconButton(
            onClick = {
                navController.navigate(Screens.LoginScreen.route)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Icon",
                tint = MaterialTheme.colors.primary
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(top = 100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AndroidView(
                {
                    customView
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            ) { view ->
                with(view) {
                    setAnimation(R.raw.forgot_pass)
                    playAnimation()
                    repeatCount = LottieDrawable.INFINITE
                    foregroundGravity = Gravity.CENTER
                }
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ConstraintLayout() {
                val (surface, fab) = createRefs()

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .constrainAs(surface) {
                            bottom.linkTo(parent.bottom)
                        },
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStartPercent = 8,
                        topEndPercent = 8
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, bottom = 50.dp),
                    ) {
                        Text(
                            text = "Recupera tu contraseña",
                            style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )

                        Text(
                            text = "Por favor ingrese su direccion email",
                            style = MaterialTheme.typography.h6.copy(
                                color = MaterialTheme.colors.primary
                            )
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            OutlinedTextField(
                                value = viewModel.emailValue.value,
                                onValueChange = { typedGmail ->
                                    viewModel.emailValue.value = typedGmail
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    //.width(342.dp)
                                    .padding(bottom = 14.dp),
                                singleLine = true,
                                label = { Text(text = "Email") },
                                placeholder = { Text("juan@email.com") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Email,
                                        contentDescription = "Name"
                                    )
                                },
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            if (viewModel.state.value.isSucessSendedEmail == false) {
                                Button(
                                    onClick = {
                                        viewModel.recoveryPassword(viewModel.emailValue.value)
                                        kc?.hide()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = MaterialTheme.colors.primaryVariant
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        //.width(342.dp)
                                        .height(64.dp)
                                        .padding(bottom = 12.dp),
                                    elevation = ButtonDefaults.elevation(4.dp)
                                ) {
                                    Text(
                                        "Recuperar",
                                        fontSize = 17.sp
                                    )
                                }
                            }
                            if(viewModel.state.value.displayPb == true){
                                CircularProgressIndicator()
                            }


                            if (viewModel.state.value.isSucessSendedEmail == true) {
                                Text(
                                    text = "Email enviado correctamente",
                                    style = MaterialTheme.typography.h3.copy(
                                        color = Color.Green
                                    )
                                )

                            }





                            if (viewModel.state.value.errorMsg != null) {
                                Snackbar(
                                    modifier = Modifier.padding(
                                        top = 0.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 6.dp
                                    ),
                                    actionOnNewLine = false,
                                    action = {
                                        TextButton(onClick = {
                                            viewModel.dismiss()
                                        }) {
                                            Text(text = "Okey")
                                        }
                                    }
                                ) {
                                    Text(text = viewModel.state.value.errorMsg!!)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}