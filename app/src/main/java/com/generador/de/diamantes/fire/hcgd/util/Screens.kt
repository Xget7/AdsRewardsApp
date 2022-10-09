package com.generador.de.diamantes.fire.hcgd.util

import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.VERIFIED_USER

sealed class Screens(val route : String) {
    object LoginScreen : Screens("login_screen")
    object RegisterScreen : Screens("register_screen")
    object UserMainScreen : Screens("user_main_screen")
    object ClaimScreen : Screens("claim_screen/{$VERIFIED_USER}"){
        fun passUserVerificated(verificado : Boolean) : String{
            return "claim_screen/$verificado"
        }
    }
    object PhoneVerificationScreen : Screens("phone_verification_screen")
    object ForgotPasswordScreen : Screens("forgot_password_screen")
    object PoliticsScreen : Screens("politics_screen")
}
