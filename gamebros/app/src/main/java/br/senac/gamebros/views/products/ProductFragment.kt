package br.senac.gamebros.views.products

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import br.senac.gamebros.databinding.FragmentProductBinding
import br.senac.gamebros.model.CartRequest
import br.senac.gamebros.model.CartResponse
import br.senac.gamebros.model.Product
import br.senac.gamebros.services.CartsService
import br.senac.gamebros.services.ProductsService
import br.senac.gamebros.utils.Constants.Companion.BASE_URL
import br.senac.gamebros.utils.Constants.Companion.RAW_URL
import br.senac.gamebros.utils.LoadingDialog
import br.senac.gamebros.views.cart.CartFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductFragment : Fragment() {
    lateinit var binding: FragmentProductBinding
    var loading = LoadingDialog(this)
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater)

        loading.startLoading()

        val bundle = arguments
        val data = bundle?.getString("data")
        data?.let {
            Log.i("Bundle data", it)

            atualizarProduto(it.toInt(), loading)
        }

        binding.btnComprarItem.setOnClickListener {
            val request = data?.toInt()?.let { it1 ->
                CartRequest(
                    user_id = 1,
                    product_id = it1
                )
            }

            addProduto(request, loading)
        }

        return binding.root
    }

    private fun addProduto(data: CartRequest?, loading: LoadingDialog) {
        loading.startLoading()
        val service = retrofit.create(CartsService::class.java)
        Log.i("data", data.toString())
        val call = data?.let { service.adicionarProdutoCarrinho(it) }

        val callback = object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if(response.isSuccessful){
                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    Log.i("RESPONSE", response.body().toString())
                    Snackbar.make(binding.container, "Produto adicionado ao carrinho.", Snackbar.LENGTH_LONG).show()
                } else {
                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    Snackbar.make(
                        binding.container,
                        "N??o foi poss??vel adicionar ao carrinho.",
                        Snackbar.LENGTH_LONG).show()
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Snackbar.make(binding.container, "N??o foi poss??vel se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar servi??o", t)
            }
        }

        call?.enqueue(callback)
    }

    fun atualizarProduto(id: Int, loading: LoadingDialog){
        val service = retrofit.create(ProductsService::class.java)
        val call = service.detalheProduto(id)

        val callback = object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful){
                    val listaProdutos = response.body()

                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    atualizarUI(listaProdutos)
                } else {

                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    Snackbar.make(binding.container, "N??o ?? poss??vel atualizar produtos", Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Snackbar.make(binding.viewBlocoDetalhe, "N??o ?? poss??vel se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar servi??o", t)
            }
        }

        call.enqueue(callback)

    }

    private fun atualizarUI(produto: Product?) {
        Picasso.get().load(RAW_URL + produto?.image).into(binding.imageDetalheProduto)
        binding.textNomeProduto.text = produto?.name
        binding.textPrecoProduto.text = "R$" + produto?.price
        binding.textDetalhesConteudo.text = produto?.description
        binding.textNomeCategoria.text = produto?.category_name
        binding.textNomeJogos.text = produto?.subCategory

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}