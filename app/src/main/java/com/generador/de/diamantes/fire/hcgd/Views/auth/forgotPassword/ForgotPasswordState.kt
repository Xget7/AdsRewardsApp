package com.generador.de.diamantes.fire.hcgd.Views.auth.forgotPassword

data class ForgotPasswordState(
    val isSucessSendedEmail : Boolean? = false,
    val displayPb: Boolean? = false,
    val errorMsg: String? = null
)
