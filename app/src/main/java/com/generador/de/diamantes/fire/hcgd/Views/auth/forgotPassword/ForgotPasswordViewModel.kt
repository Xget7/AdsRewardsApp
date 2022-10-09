package com.generador.de.diamantes.fire.hcgd.Views.auth.forgotPassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.generador.de.diamantes.fire.hcgd.presenter.UserMainPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

        val state = mutableStateOf(ForgotPasswordState())
        val emailValue  = mutableStateOf("")
        val mainPresenter = UserMainPresenter()


        @ExperimentalCoroutinesApi
        fun recoveryPassword(email: String) {
            viewModelScope.launch {
                state.value = state.value.copy(displayPb = true)
                if (email.isNotEmpty()) {
                    mainPresenter.forgotPassword(email.trim())
                        .addOnSuccessListener {
                            state.value = state.value.copy(isSucessSendedEmail = true, displayPb = false)
                        }
                        .addOnFailureListener {
                            state.value = state.value.copy(displayPb = false , errorMsg = it.localizedMessage)
                        }

                }else{
                    state.value = state.value.copy(errorMsg = "El email tiene que ser valido.")
                }
            }

        }




    fun dismiss() {
        state.value = state.value.copy(errorMsg = null)
    }
}
