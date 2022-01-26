package br.senac.gamebros.views.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.databinding.FragmentAccountBinding
import br.senac.gamebros.model.OrdersListResponse
import br.senac.gamebros.services.OrderService
import br.senac.gamebros.utils.Constants
import br.senac.gamebros.views.login.LoginActivity
import br.senac.gamebros.views.orders.OrderListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountFragment : Fragment() {
    lateinit var binding: FragmentAccountBinding
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater)

        binding.btnMeusPedidos.setOnClickListener {
            buscarPedidos(1, container)
        }

        binding.btnCadastro.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    SignupFragment.newInstance()
                ).addToBackStack("fragAccount").commit()
            }
        }

        binding.btnSair.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root

    }

    private fun buscarPedidos(userId: Int, container: ViewGroup?) {
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

                    container?.let {
                        parentFragmentManager.beginTransaction().replace(
                            it.id,
                            fragment
                        ).addToBackStack("fragCheckout").commit()
                    }
                } else {
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<List<OrdersListResponse>>, t: Throwable) {
                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call?.enqueue(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}