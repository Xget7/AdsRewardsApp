package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.reclamar

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.generador.de.diamantes.fire.hcgd.R

import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.Components.AlertDialogComponent
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.Components.ClaimCard
import com.generador.de.diamantes.fire.hcgd.model.Claim
import com.generador.de.diamantes.fire.hcgd.util.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClaimScreen(
    viewModel: ClaimViewModel = viewModel(),
    navController: NavHostController
) {
    var paypalEmail by remember {
        mutableStateOf("")
    }

    val lazyState = rememberLazyGridState()
    val screenHeigh = LocalConfiguration.current.screenHeightDp
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(30.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        navController.navigate(Screens.UserMainScreen.route) {

                        }
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back ")
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_diamond_foreground),
                        contentDescription = " Diamond"
                    )
                    Text(
                        text = "1000 = 1 usd",
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.primaryVariant,
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Escribe aqui tu Email para que te depositemos los fondos.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = paypalEmail,
                    onValueChange = { typedEmail ->
                        paypalEmail = typedEmail
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(),
                    singleLine = true,
                    label = { Text(text = "Paypal Email") },
                    placeholder = { Text("johny@email.com") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.CreditCard,
                            contentDescription = "Email"
                        )
                    },
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Opciones de retiro",
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(30.dp))

                Divider()

                Spacer(modifier = Modifier.height(30.dp))

                if (viewModel.state.value.displayAlertDialog == true) {
                    AlertDialogComponent(){
                        navController.navigate(Screens.PhoneVerificationScreen.route)
                    }
                }

                LazyVerticalGrid(
                    state = lazyState,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    columns = GridCells.Adaptive(150.dp)

                ) {
                    items(viewModel.claimAmountList) {
                        ClaimCard(amount = it) {
                            val claim = Claim(
                                idUsuario = viewModel.currentUserUid,
                                idReclamo = System.currentTimeMillis().toString(),
                                gmailDePago = paypalEmail,
                                esVip = viewModel.currentUser.value.isReferido,
                                cantidadDeReclamo = it.toInt()
                            )
                            viewModel.claimMoney(claim, context = context)
                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                if (viewModel.state.value.isLoading == true) {
                    CircularProgressIndicator()
                }

                if (viewModel.state.value.isSucess == true){

                    Snackbar(
                        modifier = Modifier.padding(
                            top = screenHeigh.dp / 15f,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 1.dp
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
                                Text(text = "Okey!", color = Color.Black)
                            }
                        }
                    ) {
                        Text(text = "Aproximadamente en 2 dias recibira su dinero en su cuenta: ${paypalEmail}", color = Color.Black)
                    }
                }

                if (viewModel.state.value.errorMsg != null) {
                    Snackbar(
                        modifier = Modifier.padding(
                            top = screenHeigh.dp / 15f,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 1.dp
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
