package br.senac.gamebros.model

data class Token (
    var id: Int,
    var name: String?,
    var email: String?,
    var password: String?,
    var phone: String?,
    var token: String
)