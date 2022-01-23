package br.senac.gamebros.api

import br.senac.gamebros.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("produtos")
    suspend fun listarProdutos() : Response<List<Product>>

    @GET("produtos/{id}")
    fun detalhesProduto(@Path("id")id: Int): Response<Product>
}