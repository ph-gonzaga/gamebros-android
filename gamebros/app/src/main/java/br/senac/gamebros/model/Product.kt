package br.senac.gamebros.model

data class Product(
    val image: String,
    val category_id: Int,
    val category_name: String,
    val subCategory: String,
    val price: String,
    val name: String,
    val description: String,
    val discount: String? = null,
    val warranty: String? = null,
    val id: Int? = null,
    val type: String? = null,
    val stock: Int? = null
)

