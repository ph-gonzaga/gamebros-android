package br.senac.gamebros.services

import br.senac.gamebros.model.Cart
import br.senac.gamebros.model.CartProductsResponse
import br.senac.gamebros.model.CartRequest
import br.senac.gamebros.model.CartResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartsService {
    @GET("carrinho/{id}")
    fun listarProdutosCarrinho(@Path("id") id: Int) : Call<Cart>

    @POST("carrinho")
    fun adicionarProdutoCarrinho(@Body request: CartRequest) : Call<CartResponse>

    @POST("carrinho/remover-produto-carrinho")
    fun removerProdutoCarrinho(@Body request: CartRequest) : Call<CartResponse>
}