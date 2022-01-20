package br.senac.gamebros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.senac.gamebros.databinding.FragmentListProductBinding
import br.senac.gamebros.databinding.ItemProductBinding
import br.senac.gamebros.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var listProducts = emptyList<Product>()

    class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.textNomeProduto.text = product.name
            binding.textPrecoProduto.text = "99.00"

            Picasso.get().load("http://pm1.narvii.com/6745/5d8f02180329ad1e6dcd431912d70cecdf0a11a0v2_00.jpg").into(binding.imageProduto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProducts[position])

    }

    fun setProducts(products: List<Product>){
        listProducts = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}