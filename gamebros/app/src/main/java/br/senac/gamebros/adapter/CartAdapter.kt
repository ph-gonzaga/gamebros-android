package br.senac.gamebros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.R
import br.senac.gamebros.model.Cart
import br.senac.gamebros.model.CartProductsResponse
import br.senac.gamebros.utils.Constants
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CartAdapter(private val cart: ArrayList<CartProductsResponse>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        // Cria o item da lista
        val itemCarrinho = LayoutInflater.from(parent?.context).inflate(R.layout.item_product_cart, parent, false)
        val holder = CartViewHolder(itemCarrinho)
        return holder
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        // Exibe o item da lista
        Picasso.get().load(Constants.RAW_URL + cart[position].image).into(holder.image)
        holder.categoria.text = cart[position].categoryName
        holder.subcategoria.text = cart[position].subCategory
        holder.nome.text = cart[position].name
        holder.preco.text = "R$" + cart[position].price

        // removendo produto do carrinho
        holder.lixeira.setOnClickListener {
            Snackbar.make(
                holder.itemView,
                "Clicado",
                Snackbar.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int = cart.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.imageProduto)
        val categoria = itemView.findViewById<TextView>(R.id.textCategoriaProduto)
        val subcategoria = itemView.findViewById<TextView>(R.id.textSubCategoriaProduto)
        val nome = itemView.findViewById<TextView>(R.id.textNomeProduto)
        val preco = itemView.findViewById<TextView>(R.id.textPrecoProduto)
        val lixeira = itemView.findViewById<ImageButton>(R.id.btnRemoveItem)
    }
}