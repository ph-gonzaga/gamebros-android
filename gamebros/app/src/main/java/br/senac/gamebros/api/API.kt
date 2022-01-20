package br.senac.gamebros.api

import br.senac.gamebros.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("produtos")
    suspend fun listarProdutos() : Response<List<Product>>
}