package br.senac.gamebros.model

data class User (
    var id: Int?,
    var name: String?,
    var email: String?,
    var phone: String?,
    var password: String?,
    var token: String?
)