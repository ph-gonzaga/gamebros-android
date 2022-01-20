package br.senac.gamebros.services

import br.senac.gamebros.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoriesService {
    @GET("categorias")
    fun listarCategorias() : Call<List<Category>>
}