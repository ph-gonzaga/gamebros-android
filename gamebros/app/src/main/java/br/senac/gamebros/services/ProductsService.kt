package br.senac.gamebros.services

import br.senac.gamebros.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductsService {
    @GET("produtos")
    suspend fun listarProdutos() : List<Product>
}