package br.senac.gamebros.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.R
import br.senac.gamebros.model.Product
import br.senac.gamebros.products.ListProductFragment

class AdapterProduct(private val produtos: MutableList<Product>): RecyclerView.Adapter<AdapterProduct.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // Cria o item da lista
        val itemLista = LayoutInflater.from(parent?.context).inflate(R.layout.item_product, parent, false)
        val holder = ProductViewHolder(itemLista)
        return holder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // Exibe o item da lista
        holder.image.setImageResource(produtos[position].image)
        holder.categoria.text = produtos[position].categoria
        holder.subcategoria.text = produtos[position].subcategoria
        holder.nome.text = produtos[position].nome
        holder.preco.text = produtos[position].preco
    }

    override fun getItemCount(): Int = produtos.size


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.imageProduto)
        val categoria = itemView.findViewById<TextView>(R.id.textCategoriaProduto)
        val subcategoria = itemView.findViewById<TextView>(R.id.textSubCategoriaProduto)
        val nome = itemView.findViewById<TextView>(R.id.textNomeProduto)
        val preco = itemView.findViewById<TextView>(R.id.textPrecoProduto)
    }
}