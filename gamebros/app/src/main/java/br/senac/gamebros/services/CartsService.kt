package br.senac.gamebros.services

import br.senac.gamebros.model.CartRequest
import br.senac.gamebros.model.CartResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CartsService {
    @POST("carrinho")
    fun adicionarProdutoCarrinho(@Body request: CartRequest) : Call<CartResponse>
}