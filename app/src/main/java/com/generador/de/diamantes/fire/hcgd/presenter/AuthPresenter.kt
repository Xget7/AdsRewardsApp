package com.generador.de.diamantes.fire.hcgd.presenter

import com.generador.de.diamantes.fire.hcgd.`interface`.AuthView
import com.generador.de.diamantes.fire.hcgd.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthPresenter : AuthView {

    private var mAuth = FirebaseAuth.getInstance()
    private var db = Firebase.database
    val usersRef = db.getReference("users")


    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUp(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signOut() {
        return mAuth.signOut()
    }

    override  suspend fun singInWithGoogle(credential: AuthCredential): Task<AuthResult> {
        return mAuth.signInWithCredential(credential)
    }


    override fun createUserInDb(user : User): Task<Void> {
        return usersRef.child(user.uid!!).setValue(user.toMap())
    }

    override  fun returnListOfUsers(): DatabaseReference {
        return usersRef
    }
}