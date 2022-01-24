package br.senac.gamebros.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.R
import br.senac.gamebros.model.Cart
import br.senac.gamebros.model.CartProductsResponse
import br.senac.gamebros.model.CartRequest
import br.senac.gamebros.model.CartResponse
import br.senac.gamebros.services.CartsService
import br.senac.gamebros.utils.Constants
import br.senac.gamebros.views.cart.CartFragment
import br.senac.gamebros.views.products.ProductFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartAdapter(private val cart: ArrayList<CartProductsResponse>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var removedPosition : Int ? = null
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        // Cria o item da lista
        val itemCarrinho = LayoutInflater.from(parent?.context).inflate(R.layout.item_product_cart, parent, false)
        val holder = CartViewHolder(itemCarrinho)


        return holder
    }

    fun getRemoveItemPosition() : Int? {
        var position = removedPosition
        return position;
    }

        override fun onBindViewHolder(holder: CartViewHolder, @SuppressLint("RecyclerView") position: Int) {
        // Exibe o item da lista
        Picasso.get().load(Constants.RAW_URL + cart[position].image).into(holder.image)
        holder.categoria.text = cart[position].categoryName
        holder.subcategoria.text = cart[position].subCategory
        holder.nome.text = cart[position].name
        holder.preco.text = "R$" + cart[position].price

        // removendo produto do carrinho
//        holder.lixeira.setOnClickListener {
//            removerProduto(cart[position].cartProductId, holder)
//        }

        holder.lixeira.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                removerProduto(cart[position].cartProductId, holder)

                cart.removeAt(position)
                notifyDataSetChanged()

                val bundle = Bundle()
                var totalPrice = 0.00

                cart.forEach {
                    totalPrice = totalPrice.plus(it.price.toFloat())
                }

                bundle.putSerializable("data", cart)
                bundle.putFloat("totalCart", totalPrice.toFloat())

                val activity = v!!.context as AppCompatActivity
                val fragment = CartFragment()
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



    private fun removerProduto(cartProductId: Int, holder: CartViewHolder) {
        val service = retrofit.create(CartsService::class.java)
        val request = CartRequest (user_id = 1, product_id = cartProductId)

        val call = service.removerProdutoCarrinho(request)

        val callback = object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if(response.isSuccessful){
                    Log.i("RESPONSE", response.body().toString())
                    //Snackbar.make(holder.itemView, "Produto excluído do carrinho.", Snackbar.LENGTH_LONG).show()
                } else{
                    Snackbar.make(
                        holder.itemView,
                        "Não foi possível adicionar ao carrinho.",
                        Snackbar.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Snackbar.make(holder.itemView, "Não foi possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call.enqueue(callback)
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