package com.generador.de.diamantes.fire.hcgd.Views.userMain.main

data class UserMainState(
    val isSuccessLoadedAds: Boolean? = false,
    val isSucess: Boolean? = false,
    val isSucessUploadDiamonds: Boolean? = false,
    val isSucessLoadedUserData: Boolean? = false,
    val isLoading: Boolean? = false,
    val isErrorMsg: String? = null,
)
