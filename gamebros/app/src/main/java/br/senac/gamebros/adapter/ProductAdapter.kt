package br.senac.gamebros.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.databinding.ItemProductBinding
import br.senac.gamebros.model.Product
import br.senac.gamebros.views.products.ProductFragment
import com.squareup.picasso.Picasso
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.senac.gamebros.R
import br.senac.gamebros.utils.Constants.Companion.RAW_URL
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.fragment_address.view.*
import kotlinx.android.synthetic.main.fragment_address.view.container
import kotlinx.android.synthetic.main.fragment_list_product.view.*
import kotlinx.android.synthetic.main.item_product.view.*


class ProductAdapter(val listener: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var listProducts = emptyList<Product>()

    class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.textNomeProduto.text = product.name
            binding.textPrecoProduto.text = "R$" + product.price

            Picasso.get().load(RAW_URL + product.image).into(binding.imageProduto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val position = holder.adapterPosition
        holder.bind(listProducts[position])

        holder.itemView.view3.setOnClickListener (object: View.OnClickListener {
            override fun onClick(v: View?) {
                val bundle = Bundle()
                bundle.putString("data", listProducts[position].id.toString())

                val activity = v!!.context as AppCompatActivity
                val fragment = ProductFragment()
                fragment.arguments = bundle

                activity
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    fun setProducts(products: List<Product>){
        listProducts = products
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}