package com.app.listaActividades.Views.Auth.Register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.generador.de.diamantes.fire.hcgd.model.User
import com.generador.de.diamantes.fire.hcgd.presenter.AuthPresenter
import com.generador.de.diamantes.fire.hcgd.util.Common
import com.generador.de.diamantes.fire.hcgd.util.Screens
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var gmail = mutableStateOf("")
    var password = mutableStateOf("")
    private var db = Firebase.database
    val usersRef = db.getReference("empleados")

    val state = mutableStateOf(LoginState())


    var FbUser = FirebaseAuth.getInstance().currentUser
    private val AuthPresenter = AuthPresenter()




    fun userLogin(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            if (
                Common.isValidString(email.trim()) &&
                Common.isValidPassword(password.trim())
            ) {
                AuthPresenter.signIn(email.trim(), password)
                    .addOnSuccessListener { auth ->
                    state.value = state.value.copy(isSuccess = true)
                    auth.user?.let {
                        navController.navigate(Screens.UserMainScreen.route)
                    }

                }.addOnFailureListener {
                    state.value = state.value.copy(isLoading = false, isError = it.message)
                }
            } else {
                state.value = state.value.copy(isError = "Rellena bien los campos",isLoading = false)
            }

        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount, navController: NavController) {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            try {
                val crendential = GoogleAuthProvider.getCredential(account.idToken, null)
                AuthPresenter.singInWithGoogle(crendential).addOnCompleteListener {
                    //verify user in database if
                    if (it.isSuccessful) {
                        if (it.result.user != null){
                            FbUser = it.result.user

                            AuthPresenter.returnListOfUsers().addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.hasChild(it.result?.user!!.uid)){
                                        state.value = state.value.copy(isSuccess = true, isLoading = false)
                                        navController.navigate(Screens.UserMainScreen.route)
                                    }else{
                                        createAccountForGoogleUser(it.result.user!!, navController)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    state.value = state.value.copy(isError = "Error con la base de datos", isLoading = false)
                                }

                            })
                        }else{
                            state.value = state.value.copy(isError = "El usuario no existe.", isLoading = false)
                        }

                    }


                }.addOnFailureListener {
                    state.value = state.value.copy(isError = it.message,isLoading = false)
                }
            } catch (e: Exception) {
                state.value = state.value.copy(isError = e.message,isLoading = false)
            }
        }


    }

    private fun createAccountForGoogleUser(FbUser: FirebaseUser, navController: NavController){
        try {
            val user = User(
                uid = FbUser.uid,
                gmail = FbUser.email,
                name = FbUser.displayName,
                diamonds= 20,
                phoneVerify = false,
                isReferido = false, //add some validation
                haveBonusDiario = true,
            )

            AuthPresenter.createUserInDb(user).addOnSuccessListener {
                state.value = state.value.copy(isSuccess = true, isLoading = false)
                navController.navigate(Screens.UserMainScreen.route)
            }.addOnFailureListener { exception ->
                state.value = state.value.copy(isError = exception.localizedMessage,isLoading = false)
            }
        } catch (e: Exception) {
            state.value = state.value.copy(isError = e.localizedMessage,isLoading = false)
        }
    }


    fun error(str: String) {
        state.value = state.value.copy(isError = str)
    }


    fun dismiss() {
        state.value = state.value.copy(isError = null)
    }
}