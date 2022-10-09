package com.generador.de.diamantes.fire.hcgd.Views.auth.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.app.listaActividades.Views.Auth.Register.LoginViewModel
import com.generador.de.diamantes.fire.hcgd.R
import com.generador.de.diamantes.fire.hcgd.Views.auth.googleButton.GoogleSignInButtonUi
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(),
) {
    val context=  LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1
    val account = GoogleSignIn.getLastSignedInAccount(LocalContext.current)
    val userWantsLogginWithGoogle = remember {
        mutableStateOf(false)
    }
    val screenHeigh = LocalConfiguration.current.screenHeightDp
    val scope = rememberCoroutineScope()

    Scaffold(
        Modifier
            .fillMaxWidth(),
        topBar = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(241.dp)
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //LOGO
            }
        },

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
                        text = "Bienvenido Nuevamente",
                        Modifier
                            .padding(top = 16.dp, bottom = 16.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                // The text will remain intact when there is configuration changes
                var email by rememberSaveable { viewModel.gmail }
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
                    //Login Button

                    Spacer(modifier = Modifier.height(36.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.userLogin(email, password, navController)
                                userWantsLogginWithGoogle.value = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ),
                        elevation = ButtonDefaults.elevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            //.width(342.dp)
                            .height(64.dp)
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            "Loguearse",
                            fontSize = 17.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    Spacer(modifier = Modifier.height(26.dp))

                    ClickableText(text = AnnotatedString(
                        "¿No tenes cuenta? , Registrate!",
                        spanStyle = SpanStyle(
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp
                        )
                    ), onClick = {
                        navController.navigate(Screens.RegisterScreen.route)
                    })
                    Spacer(modifier = Modifier.height(20.dp))

                    ClickableText(text = AnnotatedString(
                        "¿Olvidaste tu contraseña?",
                        spanStyle = SpanStyle(
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp
                        )
                    ), onClick = {
                        navController.navigate(Screens.ForgotPasswordScreen.route)
                    })

                    Spacer(modifier = Modifier.height(20.dp))
                    if (viewModel.state.value.isLoading) {
                        CircularProgressIndicator()
                    }

                }
                if (account != null) {
                    LaunchedEffect(true) {
                        navController.navigate(Screens.UserMainScreen.route)
                        navController.popBackStack()
                    }
                }
                if (viewModel.state.value.isSuccess) {
                    LaunchedEffect(true) {
                        navController.navigate(Screens.UserMainScreen.route)
                        navController.popBackStack()
                    }
                }

                if (viewModel.state.value.isError != null) {
                    Snackbar(
                        modifier = Modifier.padding(
                            top = screenHeigh.dp / 49,
                            start = 6.dp,
                            end = 6.dp,
                            bottom = 16.dp
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

            if (viewModel.FbUser != null && !viewModel.state.value.isSuccess) {
                LaunchedEffect(true) {
                    navController.popBackStack()
                    navController.navigate(Screens.UserMainScreen.route)
                }
            }
        }

    }


}