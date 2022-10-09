package com.generador.de.diamantes.fire.hcgd.model

data class Claim(
    val idUsuario : String? = null,
    val idReclamo: String? = null,
    val gmailDePago: String? = null,
    val esVip : Boolean? = false,
    val cantidadDeReclamo : Int? = null
){
    fun toMap() : HashMap<String, Any?> = hashMapOf(
        "idUsuario" to idUsuario,
        "idReclamo" to idReclamo,
        "gmailDePago" to gmailDePago,
        "esVip" to esVip,
        "cantidadDeReclamo" to cantidadDeReclamo,
    )
}
