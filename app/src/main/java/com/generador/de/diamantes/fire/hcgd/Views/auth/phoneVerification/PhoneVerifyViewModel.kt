package com.generador.de.diamantes.fire.hcgd.Views.auth.phoneVerification

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.generador.de.diamantes.fire.hcgd.model.User
import com.generador.de.diamantes.fire.hcgd.presenter.UserMainPresenter
import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.findActivity
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.generador.de.diamantes.fire.hcgd.util.TAG
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PhoneVerifyViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    val state = mutableStateOf(PhoneVerifyState())
    val phone = mutableStateOf("")
    val phoneVerificationId = mutableStateOf("")
    val presenter = UserMainPresenter()
    val currentUser = mutableStateOf(User())


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
                state.value = state.value.copy(errorMsg = e.localizedMessage, isLoading = false)
            } else if (e is FirebaseTooManyRequestsException) {
                state.value = state.value.copy(errorMsg = e.localizedMessage, isLoading = false)
            }else{
                state.value = state.value.copy(errorMsg = e.localizedMessage, isLoading = false)
            }



        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d(TAG, "onCodeSent:$verificationId")
            // Save verification ID and resending token so we can use them later
            phoneVerificationId.value = verificationId
            state.value = state.value.copy(isSucessSendedMsg = true, isLoading = false)
        }
    }


    fun startPhoneNumberVerification(context: Context) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            if (phone.value.isNotBlank()) {
                Log.e("currentphone", "not Blank" + phone.value)
                auth.setLanguageCode("es")
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+${phone.value}")       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(context.findActivity()!!)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                Log.e("currentphone", phone.value)
                try {
                    PhoneAuthProvider.verifyPhoneNumber(options)
                    state.value = state.value.copy(isLoading = false)
                }catch (e : Exception){
                    state.value = state.value.copy(errorMsg = "Error verificando telefono", isLoading = false)
                }



            } else {
                state.value = state.value.copy(errorMsg = "Ingresa un telefono!", isLoading = false)

            }

        }
    }

    fun resendVerificationCode(context: Context, token: PhoneAuthProvider.ForceResendingToken?) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+${phone.value}")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context.findActivity()!!)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .setForceResendingToken(token!!)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        Log.d("PHONEVERIFY", "$verificationId + $code ")
            if (code.isNotBlank()) {
                Log.d("PHONEVERIFY", "NOT BLANK")
                val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
                signInWithPhoneAuthCredential(credential)
            } else {
                state.value = state.value.copy(errorMsg = "Ingresa el codigo de verificacion.")
            }
    }



    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)

            auth.currentUser?.updatePhoneNumber(credential)?.addOnSuccessListener {
                presenter.updatePhoneVerification(auth.currentUser!!.uid, true).addOnSuccessListener {
                    state.value = state.value.copy(isSucessVerified = true)
                    Log.d("PHONEVERIFY", "sucess verified in db")
                }.addOnFailureListener {
                    state.value = state.value.copy(errorMsg = it.localizedMessage, isLoading = false)
                }
            }?.addOnFailureListener {
                state.value = state.value.copy(errorMsg = it.localizedMessage,  isLoading = false)
            }
        }

    }

    fun navigateTo(navController : NavController){
        viewModelScope.launch {
            delay(2000)
            navController.navigate(Screens.ClaimScreen.route)
            navController.popBackStack()
        }

    }



    fun dismiss() {
        state.value = state.value.copy(errorMsg = null)
    }
}