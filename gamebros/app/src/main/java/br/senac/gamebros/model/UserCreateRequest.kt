package br.senac.gamebros.model


import com.google.gson.annotations.SerializedName

data class UserCreateRequest(
    @SerializedName("cpf")
    val cpf: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)