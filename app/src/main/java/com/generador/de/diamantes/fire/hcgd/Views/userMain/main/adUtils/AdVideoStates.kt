package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.adUtils

data class AdVideoStates(
    val loaded : Boolean? = false,
    val dismissed : Boolean? = false,
    val failedToShow : Boolean? = false,
    val showedFullScreen : Boolean? = false,
    val isSucessReward: Boolean? = false,
    val rewardAmount: Int? = null,

)