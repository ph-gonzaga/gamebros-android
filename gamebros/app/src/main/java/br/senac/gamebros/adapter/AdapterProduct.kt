package br.senac.gamebros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.R
import br.senac.gamebros.model.Product
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterProduct(
    private val onItemClicked: (Product) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var produtos: List<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                holder.bind(produtos[position], onItemClicked)
            }
        }
    }

    fun setList(productList: ArrayList<Product>) {
        this.produtos = productList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = produtos.size

    class ProductViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName = itemView.textNomeProduto
        private val productPrice = itemView.textPrecoProduto

        fun bind(produto: Product, onItemClicked: (Product) -> Unit) {
            productName.text = produto.name
            productPrice.text = produto.price
        }
    }
}