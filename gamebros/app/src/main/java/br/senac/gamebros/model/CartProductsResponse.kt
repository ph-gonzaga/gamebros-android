package br.senac.gamebros.model


import com.google.gson.annotations.SerializedName

data class CartProductsResponse(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("cart_product_id")
    val cartProductId: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("subCategory")
    val subCategory: String,
    @SerializedName("user_id")
    val userId: Int
)