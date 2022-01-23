package br.senac.gamebros.views.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.senac.gamebros.api.RetrofitInstance.productsAPI
import br.senac.gamebros.views.cart.CartFragment
import br.senac.gamebros.databinding.FragmentProductBinding
import br.senac.gamebros.model.Product
import br.senac.gamebros.services.ProductsService
import br.senac.gamebros.utils.Constants.Companion.BASE_URL
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductFragment : Fragment() {
    lateinit var binding: FragmentProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater)

        val bundle = arguments
        val data = bundle?.getString("data")
        data?.let {
            Log.e("Bundle data", it)

            atualizarProduto(it.toInt())
        }


        binding.btnComprarItem.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    CartFragment.newInstance()
                ).addToBackStack("fragProduct").commit()
            }
        }

        return binding.root

    }

    fun atualizarProduto(id: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProductsService::class.java)
        val call = service.detalheProduto(id)

        val callback = object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful){
                    val listaProdutos = response.body()

                    atualizarUI(listaProdutos)
                } else{
                    Snackbar.make(binding.container, "Não é possível atualizar produtos", Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Snackbar.make(binding.viewBlocoDetalhe, "Não é possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call.enqueue(callback)

    }

    private fun atualizarUI(produto: Product?) {
        //binding.container.removeAllViews()

        Picasso.get().load(produto?.image).into(binding.imageDetalheProduto)
        binding.textNomeProduto.text = produto?.name
        binding.textPrecoProduto.text = "R$" + produto?.price
        binding.textDetalhesConteudo.text = produto?.description
        binding.textNomeCategoria.text = produto?.categoryId.toString()
        //binding.textCategoriaTitulo2

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}