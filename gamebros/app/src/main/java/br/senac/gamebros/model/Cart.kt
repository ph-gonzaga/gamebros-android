package br.senac.gamebros.model

data class Cart(
    val totalCarrinho: Float,
    val produtos: List<CartProductsResponse>
)

