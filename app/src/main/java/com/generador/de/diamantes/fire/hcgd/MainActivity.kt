package com.generador.de.diamantes.fire.hcgd

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.generador.de.diamantes.fire.hcgd.Views.auth.forgotPassword.ForgotPasswordScreen
import com.generador.de.diamantes.fire.hcgd.Views.auth.forgotPassword.ForgotPasswordViewModel
import com.generador.de.diamantes.fire.hcgd.Views.auth.phoneVerification.PhoneVerifyScreen
import com.generador.de.diamantes.fire.hcgd.Views.auth.phoneVerification.PhoneVerifyViewModel
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.Components.AppPoliticsScreen
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.UserMainScreen
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.UserMainViewModel
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.reclamar.ClaimScreen
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.reclamar.ClaimViewModel
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.generador.de.diamantes.fire.hcgd.util.loadRewardedAd
import com.app.listaActividades.Views.Auth.Login.RegisterScreen
import com.app.listaActividades.Views.Auth.Login.RegisterViewModel
import com.generador.de.diamantes.fire.hcgd.Views.auth.login.LoginScreen
import com.app.listaActividades.Views.Auth.Register.LoginViewModel
import com.app.listaActividades.ui.DiamantesFreeFireTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi


class MainActivity : ComponentActivity() {


    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var mRewardedVideoAd: RewardedAd


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //FIREBASE INIT
        Log.d("initializating", "lol")
         FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance(),
        )
        Log.d("Terminated initialization", "god")


        //ADS
        MobileAds.initialize(this) { loadRewardedAd(this) }


        // -- API AIzaSyBvQHzIPuBhCLe1pQJPYqgZ2JBGnp8dH0E
        val user = mAuth.currentUser
        setContent {
            DiamantesFreeFireTheme() {
                DiamantesFreeFireMainScreen(user, this@MainActivity)
            }
        }


    }


}

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiamantesFreeFireMainScreen(
    user: FirebaseUser?,
    context: Context,
) {

    val navController = rememberAnimatedNavController()
    val animationTime = 300


    AnimatedNavHost(navController = navController, startDestination = Screens.LoginScreen.route) {

        composable(
            route = Screens.RegisterScreen.route,
            enterTransition =
            {
                when (initialState.destination.route) {
                    Screens.LoginScreen.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.LoginScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screens.RegisterScreen.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screens.RegisterScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            }) {
            val viewModel = viewModel<RegisterViewModel>()
            RegisterScreen(viewModel = viewModel, navController)
        }

        composable(
            route = Screens.LoginScreen.route,
            enterTransition =
            {
                when (initialState.destination.route) {
                    Screens.LoginScreen.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.LoginScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screens.LoginScreen.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screens.LoginScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            }) {
            val viewModel = viewModel<LoginViewModel>()
            LoginScreen(navController, viewModel)
        }
        composable(
            route = Screens.UserMainScreen.route,
        ) {
            val viewModel = viewModel<UserMainViewModel>()
            UserMainScreen(viewModel, navController)
        }

        composable(
            route = Screens.PhoneVerificationScreen.route,
            enterTransition =
            {
                when (initialState.destination.route) {
                    Screens.ClaimScreen.route ->
                        slideInHorizontally(
                            animationSpec = tween(800)
                        )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.ClaimScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screens.ClaimScreen.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screens.ClaimScreen.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            }
        ) {
            val viewModel = viewModel<PhoneVerifyViewModel>()
            PhoneVerifyScreen(viewModel, navController)
        }

        composable(
            Screens.ClaimScreen.route,
            enterTransition =
            {
                when (initialState.destination.route) {
                    Screens.UserMainScreen.route ->
                        slideInVertically(
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
        ) {
            val viewModel = viewModel<ClaimViewModel>()
            ClaimScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            Screens.ForgotPasswordScreen.route,
            enterTransition =
            {
                when (initialState.destination.route) {
                    Screens.ForgotPasswordScreen.route ->
                        slideInVertically(
                            animationSpec = tween(700),
                        )
                    else -> null
                }
            },
        ) {
            val viewModel = viewModel<ForgotPasswordViewModel>()
            ForgotPasswordScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            Screens.PoliticsScreen.route,
            enterTransition =
            {
                when (initialState.destination.route) {
                    Screens.UserMainScreen.route ->
                        slideInVertically(
                            animationSpec = tween(700),
                        )
                    else -> null
                }
            },
        ) {
            AppPoliticsScreen()
        }

    }
}