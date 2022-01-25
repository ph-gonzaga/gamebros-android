package br.senac.gamebros.model

data class OrderRequest(
    val user_id: Int,
    val cart_id: Int,
    val total_price: Float
)
