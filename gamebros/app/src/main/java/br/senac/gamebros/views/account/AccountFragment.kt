package br.senac.gamebros.views.account

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.LoginActivityTest
import br.senac.gamebros.databinding.FragmentAccountBinding
import br.senac.gamebros.services.SharedPrefManager
import br.senac.gamebros.model.OrdersListResponse
import br.senac.gamebros.services.OrderService
import br.senac.gamebros.utils.Constants
import br.senac.gamebros.utils.LoadingDialog
import br.senac.gamebros.views.login.LoginActivity
import br.senac.gamebros.views.orders.OrderListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountFragment : Fragment() {
    lateinit var binding: FragmentAccountBinding
    var loading = LoadingDialog(this)
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater)
        val shared = SharedPrefManager.getInstance(requireContext())

        if(shared?.isLoggedIn){
            binding.btnMeusPedidos.setOnClickListener {
                shared?.user?.id?.let { it1 -> buscarPedidos(it1, container) }
            }
        } else {
            val i = Intent(context, LoginActivity::class.java)
            startActivity(i)
        }
   
        binding.btnCadastro.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    SignupFragment.newInstance()
                ).addToBackStack("fragAccount").commit()
            }
        }

        binding.btnSair.setOnClickListener {
            shared.clear()
//            val intent = Intent(activity, LoginActivity::class.java)
//            startActivity(intent)
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
                Log.e("ERROR", "Falha ao executar servi??o", t)

                val handler = Handler()
                handler.postDelayed(object: Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }
                }, 1000)
            }
        }

        call?.enqueue(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}