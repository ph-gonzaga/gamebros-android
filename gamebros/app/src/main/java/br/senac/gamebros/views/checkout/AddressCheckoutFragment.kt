package br.senac.gamebros.views.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.databinding.FragmentAddressCheckoutBinding
import br.senac.gamebros.model.CartResponse
import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrderResponse
import br.senac.gamebros.services.CartsService
import br.senac.gamebros.services.OrderService
import br.senac.gamebros.utils.Constants
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressCheckoutFragment : Fragment() {

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var order_id = Int ?
    lateinit var binding: FragmentAddressCheckoutBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddressCheckoutBinding.inflate(inflater)

        val bundle = arguments

        val subtotalOrder = bundle?.getFloat("subtotalOrder")
        val cartId = bundle?.getInt("cartId")
        val totalOrder = subtotalOrder?.plus(15)

        binding.textEntregaSubtotal.text = "R$"+subtotalOrder.toString()
        binding.textEntregaTotal.text = "R$"+totalOrder.toString()


        binding.btnFinalizarCompra.setOnClickListener {
            val request = cartId?.let { it1 ->
                totalOrder?.let { it2 ->
                    OrderRequest(
                        user_id = 1,
                        cart_id = it1,
                        total_price = it2
                    )
                }
            }
            criarPedido(request)

            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    PurchaseFragment.newInstance()
                ).addToBackStack("fragCheckout").commit()
            }
        }

        return binding.root
    }

    private fun criarPedido(request: OrderRequest?) {
        val service = retrofit.create(OrderService::class.java)
        val call = request?.let { service.criarPedido(it) }
        var order_id = Int
        val callback = object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>){
                if(response.isSuccessful){
                    order_id = response.body()?.order_id
                } else{
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call?.enqueue(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddressCheckoutFragment()
    }
}