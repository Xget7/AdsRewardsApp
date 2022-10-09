package com.generador.de.diamantes.fire.hcgd.presenter

import com.generador.de.diamantes.fire.hcgd.`interface`.UserMainView
import com.generador.de.diamantes.fire.hcgd.model.Claim
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserMainPresenter : UserMainView {

    private var mAuth = FirebaseAuth.getInstance()
    private var db = Firebase.database
    val usersRef = db.getReference("users")

    override suspend fun getUserData(): DatabaseReference {
        return usersRef.child(mAuth.currentUser!!.uid)
    }

    override fun updateUserDiamonds(diamonds: Int): Task<Void> {
        return usersRef.child(mAuth.currentUser!!.uid).child("diamonds").setValue(diamonds)
    }

    override suspend fun updateUserBonus(userId: String, lastBonus: Long): Task<Void> {
        usersRef.child(userId).child("haveBonusDiario").setValue(false)
        return usersRef.child(userId).child("lastBonusDiario").setValue(lastBonus)
    }

    override suspend fun updateUserRewardAd(userId: String, lastBonus: Long): Task<Void> {
        return usersRef.child(userId).child("lastRewardAd").setValue(lastBonus)
    }


    override suspend fun claimDiamonds(userId : String, claim : Claim): Task<Void> {

        return db.getReference("reclamos").child("$userId + ${claim.idReclamo}").setValue(claim.toMap())
    }

    override suspend fun forgotPassword(gmail: String): Task<Void> {
        return mAuth.sendPasswordResetEmail(gmail)
    }

    override  fun updatePhoneVerification(userId: String, verificated : Boolean): Task<Void> {
        return usersRef.child(userId).child("phoneVerify").setValue(verificated)
    }


}