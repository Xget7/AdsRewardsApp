package com.generador.de.diamantes.fire.hcgd.model

data class User(
    val uid: String? = null,
    val gmail : String? = null,
    val name : String? = null,
    val diamonds : Int? = null,
    val isReferido : Boolean? = null,
    val phoneVerify : Boolean? = null,
    val codigoDeReferido : String? = null,
    val haveBonusDiario  : Boolean? = null,
    val lastBonusDiario: Long? = null,
    val lastRewardAd: Long? = null,
    val creatorCode: String? = null,
    val gmailDePago : String? = null,
){
    fun toMap() : HashMap<String, Any?> = hashMapOf(
        "uid" to uid,
        "gmail" to gmail,
        "name" to name,
        "diamonds" to diamonds,
        "isReferido" to isReferido,
        "phoneVerify" to phoneVerify,
        "codigoDeReferido" to codigoDeReferido,
        "haveBonusDiario" to haveBonusDiario,
        "lastBonusDiario" to lastBonusDiario,
        "lastRewardAd" to lastRewardAd,
        "creatorCode" to creatorCode,
        "gmailDePago" to gmailDePago
    )
}
