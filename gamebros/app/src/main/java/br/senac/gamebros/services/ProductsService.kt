package br.senac.gamebros.services

import br.senac.gamebros.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsService {
    @GET("produtos")
    fun listarProdutos() : List<Product>

    @GET("produtos/{id}")
    fun detalheProduto(@Path("id") id: Int) : Call<Product>
}