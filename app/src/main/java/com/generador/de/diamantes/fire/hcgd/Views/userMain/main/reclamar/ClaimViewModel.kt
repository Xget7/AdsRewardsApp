package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.reclamar

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.generador.de.diamantes.fire.hcgd.model.Claim
import com.generador.de.diamantes.fire.hcgd.model.User
import com.generador.de.diamantes.fire.hcgd.presenter.UserMainPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClaimViewModel constructor(
    private var savedStateHandle : SavedStateHandle
) : ViewModel() {

    private val isVerificado = mutableStateOf(false)
    private var fb = FirebaseAuth.getInstance()
    val claimAmountList = listOf("5","10","20","50","100","999")

    val state = mutableStateOf(ClaimState())
    val presenter = UserMainPresenter()
    val currentUserUid = fb.currentUser?.uid
    val currentUser = mutableStateOf(User())

    init {
        getUser()
    }


    private fun getUser() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            presenter.getUserData().addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null){
                        currentUser.value = user
                        isVerificado.value = user.phoneVerify!!
                        state.value = state.value.copy(isLoading = false)
                        Log.e("CurrentUseruid=", user.toString())
                        return
                    }else{
                        state.value = state.value.copy(
                            errorMsg = "No se encontro usuario",
                            isLoading = false
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    state.value = state.value.copy(errorMsg = error.message, isLoading = false)
                }
            })
        }

    }

    fun claimMoney(claim: Claim, context: Context) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            delay(1000)
            Log.e("isVerified?", isVerificado.value.toString())
            if (isVerificado.value){
                if (!claim.gmailDePago.isNullOrBlank()){
                    if (currentUser.value.diamonds!! >= (claim.cantidadDeReclamo?.times(1000)!!)){
                        presenter.claimDiamonds(currentUserUid!!,claim).addOnSuccessListener {
                            var currentDiamonds = currentUser.value.diamonds!! - claim.cantidadDeReclamo.times(1000)
                            presenter.updateUserDiamonds(currentDiamonds).addOnSuccessListener {
                                state.value = state.value.copy(isSucess = true, isLoading = false )
                            }.addOnFailureListener {
                                state.value = state.value.copy(errorMsg = it.localizedMessage)
                            }
                        }.addOnFailureListener {
                            state.value = state.value.copy(errorMsg = it.localizedMessage)
                        }
                    }else{
                        state.value = state.value.copy(errorMsg = "No tienes los suficientes diamantes para canjear", isLoading = false)
                    }

                }else{
                    state.value = state.value.copy(errorMsg = "Ingresa un email de paypal.", isLoading = false)
                }
            }else{
                state.value = state.value.copy(isLoading = false, displayAlertDialog = true)
            }
        }

    }


    fun dismiss() {
        state.value = state.value.copy(errorMsg = null)
    }




}