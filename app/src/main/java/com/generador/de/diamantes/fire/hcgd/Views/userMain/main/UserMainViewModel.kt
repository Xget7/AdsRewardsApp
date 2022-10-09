package com.generador.de.diamantes.fire.hcgd.Views.userMain.main

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.generador.de.diamantes.fire.hcgd.BuildConfig
import com.generador.de.diamantes.fire.hcgd.Views.userMain.main.adUtils.AdVideoStates
import com.generador.de.diamantes.fire.hcgd.model.User
import com.generador.de.diamantes.fire.hcgd.presenter.UserMainPresenter
import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.findActivity
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.generador.de.diamantes.fire.hcgd.util.loadRewardedAd
import com.generador.de.diamantes.fire.hcgd.util.mRewardedAd
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class UserMainViewModel : ViewModel() {

    val mainPresenter = UserMainPresenter()
    val mAuth = FirebaseAuth.getInstance()
    val user = mAuth.currentUser
    val currentUser = mutableStateOf(User())

    val userName = mutableStateOf("")
    val userDiamonds = mutableStateOf(0)
    val state = mutableStateOf(UserMainState())
    val videAdState = mutableStateOf(AdVideoStates())
    val bonusDiario = mutableStateOf(false)
    val showBonusDiario = mutableStateOf(false)
    val showRewardAd = mutableStateOf(false)
    val canShowRewardAd = mutableStateOf(false)


    init {
        getUser()
    }

    fun goToPolitics(navController: NavController) {
        navController.navigate(Screens.PoliticsScreen.route)
    }

    private fun getUser() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            mainPresenter.getUserData().addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null){
                        parseUserData(user)
                        currentUser.value = user
                        state.value = state.value.copy(isLoading = false)
                        Log.e("CurrentUseruid=", user.toString())
                        return
                    }else{
                        state.value = state.value.copy(
                            isErrorMsg = "No se encontro usuario",
                            isLoading = false
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    state.value = state.value.copy(isErrorMsg = error.message, isLoading = false)
                }
            })
        }

    }

    fun changeLastAdOpen() {
        viewModelScope.launch {
            if (canShowRewardAd.value) {
                showRewardAd.value = true
                val _lastBonus = System.currentTimeMillis()
                mainPresenter.updateUserRewardAd(currentUser.value.uid!!, _lastBonus)
                    .addOnSuccessListener {
                        showRewardAd.value = false
                        canShowRewardAd.value = false
                    }.addOnFailureListener {
                        state.value =
                            state.value.copy(isErrorMsg = it.localizedMessage, isLoading = false)
                    }
            } else {
                state.value = state.value.copy(
                    isErrorMsg = "Tienes que esperar dos minutos antes ver otro anuncio",
                    isLoading = false
                )

            }
        }
    }
    fun changeBonusDiario() {
        viewModelScope.launch {
            if (bonusDiario.value) {
                showBonusDiario.value = true
                val _lastBonus = System.currentTimeMillis()
                mainPresenter.updateUserBonus(currentUser.value.uid!!, _lastBonus)
                    .addOnSuccessListener {
                        showBonusDiario.value = false
                        bonusDiario.value = false
                    }.addOnFailureListener {
                    state.value =
                        state.value.copy(isErrorMsg = it.localizedMessage, isLoading = false)
                }
            } else {
                state.value = state.value.copy(
                    isErrorMsg = "Tu bonus todavia no esta disponible",
                    isLoading = false
                )

            }
        }

    }

    fun shareApp() : Intent?{
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "FreeFireDiamonds")
            var shareMessage = "\nTe recomiendo esta apliacion para obtener diamates gratis en Free Fire!\n\n"
            shareMessage =
                """${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}""".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            return shareIntent
        } catch (e: Exception) {
            state.value = state.value.copy(isErrorMsg = e.localizedMessage,)
        }
        return null
    }


    fun loadAdListener(context: Context) {
        viewModelScope.launch {
            AdView(context).apply {
                loadRewardedAd(context)
                mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d("VIEWMODEL", "Ad was dismissed.")
                        mRewardedAd = null
                        loadRewardedAd(context)
                        videAdState.value = videAdState.value.copy(dismissed = true)
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        Log.d("VIEWMODEL", "Ad failed to show.")
                        videAdState.value = videAdState.value.copy(failedToShow = true)
                        mRewardedAd = null
                        loadRewardedAd(context)
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d("VIEWMODEL", "Ad showed fullscreen content.")
                        videAdState.value = videAdState.value.copy(showedFullScreen = true)
                        loadRewardedAd(context)
                    }
                }
                if (mRewardedAd != null) {
                    val activity = context.findActivity()
                    mRewardedAd?.show(
                        activity
                    ) { rewardItem ->
                        val rewardAmount = rewardItem.amount
                        addDiamonds(rewardAmount)
                        Log.d("TAG", "User earned the reward.")
                    }
                } else {
                    state.value = state.value.copy(isErrorMsg = "El anuncio todavia no cargo")
                    loadRewardedAd(context)
                }

            }
        }

    }

    private fun addDiamonds(diamonds: Int) {
        userDiamonds.value += diamonds
        viewModelScope.launch {
            mainPresenter.updateUserDiamonds(userDiamonds.value).addOnCompleteListener {
                if (it.isSuccessful) {
                    state.value = state.value.copy(isSucessUploadDiamonds = true)
                }
            }.addOnFailureListener {
                state.value = state.value.copy(isErrorMsg = it.localizedMessage, isLoading = false)

            }
        }
    }


    fun signOut(navController: NavController) {
        viewModelScope.launch {
            mAuth.signOut()
            navController.popBackStack()
            navController.navigate(Screens.LoginScreen.route)
        }

    }


    private fun parseUserData(user: User) {
        val currentTime = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO) {
            user.name?.let { userName.value = it }
            user.diamonds?.let { userDiamonds.value = it }
            if (user.lastBonusDiario == null) {
                bonusDiario.value = true
            } else {
                val hoursBonus = TimeUnit.MILLISECONDS.toHours(user.lastBonusDiario)
                val hoursNow = TimeUnit.MILLISECONDS.toHours(currentTime)
                bonusDiario.value = (hoursBonus - hoursNow) <= -24
                Log.e("BonusDIario?"," ${bonusDiario.value}  + ${(hoursBonus - hoursNow)}")
            }
            if (user.lastRewardAd == null){
                canShowRewardAd.value = true
            }else{
                val minLastReward = TimeUnit.MILLISECONDS.toMinutes(user.lastRewardAd)
                val minNow = TimeUnit.MILLISECONDS.toMinutes(currentTime)
                canShowRewardAd.value = (minLastReward - minNow) <= -60
            }

        }
    }


    fun dismiss() {
        state.value = state.value.copy(isErrorMsg = null)
    }

}