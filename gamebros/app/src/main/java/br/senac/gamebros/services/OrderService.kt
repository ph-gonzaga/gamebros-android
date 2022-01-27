package br.senac.gamebros.services

import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrderResponse
import br.senac.gamebros.model.OrdersListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("pedidos/{id}")
    fun buscarPedidos(@Path("id") id: Int) : Call<List<OrdersListResponse>>

    @POST("pedidos")
    fun criarPedido(@Body request: OrderRequest) : Call<OrderResponse>
}