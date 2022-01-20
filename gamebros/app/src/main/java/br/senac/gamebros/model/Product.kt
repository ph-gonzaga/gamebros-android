package br.senac.gamebros.model

data class Product(
    val image: String,
    val categoryId: Int,
    val price: String,
    val name: String,
    val description: String,
    val discount: String? = null,
    val warranty: String? = null,
    val id: Int? = null,
    val type: String? = null,
    val stock: Int? = null
)

