package br.senac.gamebros.services

import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrderResponse
import br.senac.gamebros.model.UserCreateRequest
import br.senac.gamebros.model.UserCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("usuarios")
    fun criarUsuario(@Body request: UserCreateRequest) : Call<UserCreateResponse>
}