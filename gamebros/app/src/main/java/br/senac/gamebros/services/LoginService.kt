package br.senac.gamebros.services

import br.senac.gamebros.model.Login
import br.senac.gamebros.model.Token
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    fun fazerLogin(@Body login: Login): Call<Token>

    @POST("logout")
    fun fazerLogout(@Header("Authorization") auth: String): Call<String>
}