package br.senac.gamebros.services

import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {
    @POST("pedidos")
    fun criarPedido(@Body request: OrderRequest) : Call<OrderResponse>
}