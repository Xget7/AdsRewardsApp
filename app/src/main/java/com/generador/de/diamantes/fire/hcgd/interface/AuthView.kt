package com.generador.de.diamantes.fire.hcgd.`interface`

import com.generador.de.diamantes.fire.hcgd.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference

interface AuthView {
    suspend fun signIn(email : String, password : String) : Task<AuthResult>

    suspend fun signUp(email : String, password : String) : Task<AuthResult>

    suspend fun signOut()

    suspend fun singInWithGoogle(credential: AuthCredential) : Task<AuthResult>

    fun createUserInDb(user : User) : Task<Void>

    fun returnListOfUsers() : DatabaseReference

}