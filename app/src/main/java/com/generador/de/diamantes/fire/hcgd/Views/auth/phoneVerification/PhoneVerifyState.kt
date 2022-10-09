package com.generador.de.diamantes.fire.hcgd.Views.auth.phoneVerification

data class PhoneVerifyState (
    val isSucessSendedMsg : Boolean? = false,
    val isSucessVerified : Boolean? = false,
    val isLoading: Boolean? = false,
    val errorMsg : String? = null
        )