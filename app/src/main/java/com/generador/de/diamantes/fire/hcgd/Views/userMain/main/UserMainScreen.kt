package com.generador.de.diamantes.fire.hcgd.Views.userMain.main

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.generador.de.diamantes.fire.hcgd.R
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.generador.de.diamantes.fire.hcgd.util.loadRewardedAd
import com.google.android.gms.ads.*
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserMainScreen(
    viewmodel: UserMainViewModel = viewModel(),
    navController: NavHostController
) {


    val adWidth = LocalConfiguration.current.screenWidthDp - 32
    val screenHeigh = LocalConfiguration.current.screenHeightDp - 120
    val context = LocalContext.current
    val customView = remember {
        LottieAnimationView(context).apply {
            scaleType = ImageView.ScaleType.FIT_XY
        }
    }

    var userDiamonds = remember {
        viewmodel.userDiamonds
    }

    val shareApp =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }


    val coroutine = rememberCoroutineScope()




    val userDiamondsCounter by animateIntAsState(
        targetValue = userDiamonds.value,
        animationSpec = tween(
            durationMillis = 4000,
            easing = FastOutSlowInEasing
        )
    )
    LaunchedEffect(Unit) {
        userDiamonds.value = userDiamonds.value
    }

    if (viewmodel.showRewardAd.value) {
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    loadRewardedAd(context)
                    viewmodel.loadAdListener(context)
                    viewmodel.changeLastAdOpen()
                }

            }
        )
    }

    if (viewmodel.showBonusDiario.value) {
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    loadRewardedAd(context)
                    viewmodel.loadAdListener(context)
                    viewmodel.changeBonusDiario()
                }

            }
        )
    }

    if (viewmodel.videAdState.value.isSucessReward == true) {
        viewmodel.showRewardAd.value = false
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = MaterialTheme.colors.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = MaterialTheme.colors.primaryVariant),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = {
                        viewmodel.signOut(navController)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = "Log out",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(220.dp))

                    Text(
                        text = "Politicas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White

                    )

                    IconButton(onClick = {
                        viewmodel.goToPolitics(navController)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info Politica",
                            tint = Color.White
                        )
                    }


                }
            }
        }
    ) {
        if (viewmodel.state.value.isLoading == true) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }

            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    //user info
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier
                            .width(adWidth.dp)
                            .height(100.dp),
                        elevation = 6.dp,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            viewmodel.userName.value.let { name ->
                                Text(
                                    text = name,
                                    modifier = Modifier
                                        .padding()
                                        .width(180.dp),
                                    fontSize = 30.sp,
                                    color = Color.White,
                                )
                            }



                            userDiamondsCounter.let { diamonds ->
                                Image(
                                    painter = painterResource(id = R.drawable.ic_diamond_foreground),
                                    contentDescription = " Diamond"
                                )

                                Text(
                                    text = "$diamonds",
                                    modifier = Modifier
                                        .padding()
                                        .width(200.dp),
                                    fontSize = 30.sp,
                                    color = Color.White
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Yellow)
                            ){
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                TextButton(onClick = {
                                   coroutine.launch {
                                       viewmodel.changeLastAdOpen()
                                   }
                                }) {
                                    Text(
                                        text = "Generar Diamantes",
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        fontSize = 25.sp,
                                        color = Color.White,
                                        style = TextStyle(
                                            shadow = Shadow(color = Color.Black, blurRadius = 5f)
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                        }


                    }


                    Spacer(modifier = Modifier.height(140.dp))


                    Box(
                        modifier = Modifier
                            .width(220.dp)
                            .height(60.dp)
                    ) {
                        AndroidView(
                            factory = {
                                customView
                            },
                            modifier = Modifier
                                .width(220.dp)
                                .height(60.dp)
                        ) { view ->
                            with(view) {
                                setAnimation(R.raw.gradient_btn)
                                playAnimation()
                                repeatCount = LottieDrawable.INFINITE
                                foregroundGravity = Gravity.CENTER
                            }
                        }

                        TextButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Text(
                                text = "Pase vip",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding()
                                    .width(220.dp)
                                    .height(60.dp),
                                fontSize = 30.sp,
                                color = Color.White
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(40.dp))


                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            shape = CircleShape,
                            modifier = Modifier
                                .height(50.dp),
                            onClick = {
                                coroutine.launch {
                                    viewmodel.changeBonusDiario()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            ),
                            elevation = ButtonDefaults.elevation(4.dp),

                            ) {
                            Text(
                                text = "Bonus Diario",
                                modifier = Modifier,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(70.dp))

                        Button(
                            shape = CircleShape,
                            modifier = Modifier
                                .height(50.dp),
                            onClick = {
                                navController.navigate(
                                    Screens.ClaimScreen.passUserVerificated(
                                        viewmodel.currentUser.value.phoneVerify!!
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            ),
                            elevation = ButtonDefaults.elevation(4.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.dollarsymbol),
                                contentDescription = "Dolar",
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text(
                                text = "Reclamar",
                                modifier = Modifier,
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        shape = CircleShape,
                        modifier = Modifier
                            .height(50.dp),
                        onClick = {
                            shareApp.launch(viewmodel.shareApp())
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        ),
                        elevation = ButtonDefaults.elevation(3.dp),


                        ) {
                        Icon(imageVector = Icons.Default.IosShare, contentDescription = "Share")
                        Text(
                            text = "Invita Amigos",
                            modifier = Modifier,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                    if (viewmodel.state.value.isErrorMsg != null) {
                        Snackbar(
                            modifier = Modifier.padding(
                                top = 20.dp,
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
                                        viewmodel.dismiss()
                                    },
                                ) {
                                    Text(text = "Okey", color = Color.Gray)
                                }
                            }
                        ) {
                            Text(text = viewmodel.state.value.isErrorMsg!! , color = Color.Black)
                        }
                    }
                }


                // shows a traditional banner test ad
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = screenHeigh.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    AndroidView(
                        factory = { context ->
                            AdView(context).apply {
                                adSize = AdSize.BANNER
                                adUnitId = context.getString(R.string.banner_ad_unit_id)
                                this.loadAd(AdRequest.Builder().build())
                            }

                        }
                    )
                }


            }
        }


    }


}