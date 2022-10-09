package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.reclamar

data class ClaimState(
    val isSucess : Boolean? = false,
    val isLoading: Boolean? = false,
    val errorMsg: String? = null,
    val displayAlertDialog : Boolean? = false

)
