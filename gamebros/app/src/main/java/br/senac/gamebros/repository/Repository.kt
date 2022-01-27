package br.senac.gamebros.repository

import br.senac.gamebros.api.RetrofitInstance
import br.senac.gamebros.model.Product
import retrofit2.Response

class Repository {
    suspend fun listarProdutos(categoryId: Int): Response<List<Product>> {
        return RetrofitInstance.productsAPI.listarProdutos(categoryId)
    }
}