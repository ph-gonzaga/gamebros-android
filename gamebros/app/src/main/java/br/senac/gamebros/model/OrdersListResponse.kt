package br.senac.gamebros.model

data class OrdersListResponse(
    val id: Int,
    val user_id: Int,
    val cart_id: Int,
    val total_price: Float,
    val cep: String,
    val address: String,
    val address_number: Int,
    val address_city: String,
    val address_uf: String,
    val address_complement: String? = null
)
