package com.app.listaActividades.Views.Auth.Login

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.generador.de.diamantes.fire.hcgd.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember {
        mutableStateOf(viewModel.user)
    }
    val screenHeigh = LocalConfiguration.current.screenHeightDp

    val signInRequestCode = 1



    Scaffold(
        topBar = {
            Box {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(201.dp)
                        .background(MaterialTheme.colors.primary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                }

            }
        }
    ) {
        ConstraintLayout() {
            Column(
                Modifier
                    .padding(start = 36.dp, end = 36.dp)
                    .fillMaxSize()
            ) {

                Column(
                    Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Bienvenido!",
                        Modifier
                            .padding(top = 16.dp, bottom = 16.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                // The text will remain intact when there is configuration changes
                var email by rememberSaveable { viewModel.gmail }
                var userName by rememberSaveable { viewModel.userName }
                var password by rememberSaveable { viewModel.password }


                Column(
                    Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        value = email,
                        onValueChange = { typedEmail ->
                            email = typedEmail
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            //.width(342.dp)
                            .padding(bottom = 14.dp),
                        singleLine = true,
                        label = { Text(text = "Email") },
                        placeholder = { Text("john@email.com") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "Email"
                            )
                        },
                    )

                    OutlinedTextField(
                        value = userName,
                        onValueChange = { typedName ->
                            userName = typedName
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            //.width(342.dp)
                            .padding(bottom = 14.dp),
                        singleLine = true,
                        label = { Text(text = "Nombre") },
                        placeholder = { Text("john") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Name"
                            )
                        },
                    )


                    // Password visibility will remain intact when there is configuration changes
                    var passwordVisibility by remember {
                        mutableStateOf(false)
                    }

                    // Icon button for visibility of password
                    val passwordTrailingIcon = if (passwordVisibility)
                        painterResource(id = R.drawable.ic_twotone_visibility_on)
                    else
                        painterResource(id = R.drawable.ic_twotone_visibility_off)

                    OutlinedTextField(
                        value = password,
                        onValueChange = { typedPassword ->
                            password = typedPassword
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        //.width(342.dp),
                        singleLine = true,
                        label = { Text(text = "Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = "Password"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    passwordVisibility = !passwordVisibility
                                }
                            ) {
                                Icon(
                                    painter = passwordTrailingIcon,
                                    contentDescription = "Password Visibility"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordVisibility) VisualTransformation.None
                        else
                            PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(36.dp))


                    Spacer(modifier = Modifier.height(36.dp))
                    Button(
                        onClick = {
                            viewModel.registrar(email, password,navController)
                        },
                        Modifier
                            .fillMaxWidth()
                            //.width(342.dp)
                            .height(64.dp)
                            .padding(bottom = 12.dp),
                        elevation = ButtonDefaults.elevation(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ),

                    ) {
                        Text(
                            "Registrarse",
                            fontSize = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(26.dp))

                    ClickableText(text = AnnotatedString(
                        "¿Ya tenes cuenta?",
                        spanStyle = SpanStyle(
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp
                        )
                    ), onClick = {
                        navController.navigate(Screens.LoginScreen.route)
                    })

                    if (viewModel.state.value.isError != null) {
                        Snackbar(
                            modifier = Modifier.padding(
                                top = screenHeigh.dp / 15f,
                                start = 6.dp,
                                end = 6.dp,
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
                            Text(text = viewModel.state.value.isError!!)
                        }
                    }

                }

                if (viewModel.state.value.isLoading) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                }
               
                if (viewModel.user != null && !viewModel.state.value.isSuccess) {
                    LaunchedEffect(true) {
                        navController.navigate(Screens.UserMainScreen.route)
                        navController.popBackStack()
                    }
                }

            }
        }





    }


}

