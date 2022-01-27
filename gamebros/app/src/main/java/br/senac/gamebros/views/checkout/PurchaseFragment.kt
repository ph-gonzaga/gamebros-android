package br.senac.gamebros.views.checkout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.LoginActivityTest
import br.senac.gamebros.databinding.FragmentPurchaseBinding
import br.senac.gamebros.model.CartProductsResponse
import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrderResponse
import br.senac.gamebros.model.OrdersListResponse
import br.senac.gamebros.services.OrderService
import br.senac.gamebros.services.SharedPrefManager
import br.senac.gamebros.utils.Constants
import br.senac.gamebros.utils.LoadingDialog
import br.senac.gamebros.views.orders.OrderListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchaseFragment : Fragment() {
    lateinit var binding: FragmentPurchaseBinding
    var loading = LoadingDialog(this)
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPurchaseBinding.inflate(inflater)

        val bundle = arguments
        val orderId = bundle?.getInt("orderId")
        Log.i("orderID", orderId.toString())

        binding.textPedidoNumero.text = "#"+("00000000" + orderId.toString()).takeLast(4)

        binding.btnAcessarPedidos.setOnClickListener {
            val shared = SharedPrefManager.getInstance(requireContext())

            Log.i("USERID", shared.user.id.toString())

            if(shared.isLoggedIn){
                shared.user.id?.let { it1 -> buscarPedidos(it1, container) }
            } else {
                val i = Intent(context, LoginActivityTest::class.java)
                startActivity(i)
            }
        }

        return binding.root
    }

    private fun buscarPedidos(userId: Int, container: ViewGroup?) {
        loading.startLoading()
        val service = retrofit.create(OrderService::class.java)
        val call = userId?.let { service.buscarPedidos(it) }

        val callback = object : Callback<List<OrdersListResponse>> {
            override fun onResponse(call: Call<List<OrdersListResponse>>, response: Response<List<OrdersListResponse>>){
                if(response.isSuccessful){

                    Log.i("callback", response.body().toString())
                    val listaPedidos = response.body()
                    val list = ArrayList<OrdersListResponse>(listaPedidos)

                    val bundle = Bundle()

                    bundle.putSerializable("data", list)

                    val fragment = OrderListFragment.newInstance()
                    fragment.arguments = bundle

                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    container?.let {
                        parentFragmentManager.beginTransaction().replace(
                            it.id,
                            fragment
                        ).addToBackStack("fragCheckout").commit()
                    }
                } else {
                    val handler = Handler()
                    handler.postDelayed(object: Runnable {
                        override fun run() {
                            loading.isDismiss()
                        }
                    }, 1000)

                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<List<OrdersListResponse>>, t: Throwable) {
                val handler = Handler()
                handler.postDelayed(object: Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }
                }, 1000)

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call?.enqueue(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PurchaseFragment()
    }
}