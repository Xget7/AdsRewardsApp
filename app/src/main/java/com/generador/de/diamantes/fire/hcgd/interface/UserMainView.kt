package com.generador.de.diamantes.fire.hcgd.`interface`

import com.generador.de.diamantes.fire.hcgd.model.Claim
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference

interface UserMainView {

    suspend fun getUserData() : DatabaseReference

    fun updateUserDiamonds(diamonds: Int)  : Task<Void>

    suspend fun updateUserBonus(userId: String,lastBonus: Long)  : Task<Void>

    suspend fun claimDiamonds(userId : String, claim : Claim) : Task<Void>

    suspend fun forgotPassword(gmail : String) : Task<Void>

     fun updatePhoneVerification(userId: String, verificated: Boolean) : Task<Void>

    suspend fun updateUserRewardAd(userId: String, lastBonus: Long): Task<Void>
}