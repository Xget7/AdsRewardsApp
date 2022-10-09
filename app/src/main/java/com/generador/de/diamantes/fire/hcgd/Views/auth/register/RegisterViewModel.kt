package com.app.listaActividades.Views.Auth.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.generador.de.diamantes.fire.hcgd.model.User
import com.generador.de.diamantes.fire.hcgd.presenter.AuthPresenter
import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.isValidName
import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.isValidPassword
import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.isValidString
import com.generador.de.diamantes.fire.hcgd.util.Screens

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class RegisterViewModel constructor(

): ViewModel(){



    var gmail = mutableStateOf("")
    var userName = mutableStateOf("")
    var codigoReferido = mutableStateOf("")
    var password = mutableStateOf("")
    init {

    }
    val state = mutableStateOf(RegisterState())

    val user = FirebaseAuth.getInstance().currentUser
    val authPresenter = AuthPresenter()

    fun registrar(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true, isSuccess = false)
            if (isValid(email, password, userName.value)) {
                authPresenter.signUp(email.trim(), password).addOnSuccessListener {
                        try {
                            val user = User(
                                uid = it.user?.uid,
                                gmail = gmail.value,
                                name = userName.value,
                                diamonds= 20,
                                phoneVerify = false,
                                isReferido = codigoReferido.value.trim().isNotEmpty(), //add some validation
                                haveBonusDiario = true,
                                codigoDeReferido = null,
                                lastBonusDiario = null,
                                lastRewardAd = null,
                                creatorCode = null,
                                gmailDePago = null
                            )
                            authPresenter.createUserInDb(user).addOnSuccessListener {
                                navController.navigate(Screens.UserMainScreen.route)
                                state.value = state.value.copy(isSuccess = true)
                            }.addOnFailureListener { exception ->
                                state.value = state.value.copy(isError = exception.localizedMessage)
                            }
                        } catch (e: Exception) {
                            state.value = state.value.copy(isError = e.localizedMessage)
                        }
                }.addOnFailureListener {
                    state.value = state.value.copy(isLoading = false)
                    state.value = state.value.copy(isError = it.localizedMessage)
                }
            }
            else {
                state.value = state.value.copy(isLoading = false)
            }

        }
    }

    private fun isValid(email: String, password: String, username :  String): Boolean {
        if (!isValidString(email.trim())){
            state.value = state.value.copy(isError = "El Email no es valido")
            return false
        }else if (!isValidPassword(password.trim())){
            state.value = state.value.copy(isError = "La contraseña no es valida, debe ser mayor a 6 caracteres")
            return false
        }else if (!isValidName(username.trim())){
            state.value = state.value.copy(isError = "El nombre no es valido, debe ser mayor a 4 caracteres")
            return false
        }
        return true
    }

    fun error(str : String){
        state.value = state.value.copy(isError = str)
    }

    fun dismiss() {
        state.value = state.value.copy(isError = null)
    }


}