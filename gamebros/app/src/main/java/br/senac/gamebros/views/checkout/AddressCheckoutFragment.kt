package br.senac.gamebros.views.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.api.ViaCepAPI
import br.senac.gamebros.databinding.FragmentAddressCheckoutBinding
import br.senac.gamebros.model.CartResponse
import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrderResponse
import br.senac.gamebros.model.ViaCep
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
    lateinit var binding: FragmentAddressCheckoutBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddressCheckoutBinding.inflate(inflater)

        val bundle = arguments

        val subtotalOrder = bundle?.getFloat("subtotalOrder")
        val cartId = bundle?.getInt("cartId")
        val totalOrder = subtotalOrder?.plus(15)
        val cep = binding.editFieldCEP

        binding.textEntregaSubtotal.text = "R$"+subtotalOrder.toString()
        binding.textEntregaTotal.text = "R$"+totalOrder.toString()


        binding.btnFinalizarCompra.setOnClickListener {
            var addressNumberTrat = 0
            if(binding.editFieldNumero.text.toString().isNotEmpty()){
                addressNumberTrat = binding.editFieldNumero.text.toString().toInt()
            }

            val request = cartId?.let { it1 ->
                totalOrder?.let { it2 ->
                    OrderRequest(
                        user_id = 1,
                        cart_id = it1,
                        total_price = it2,
                        cep = cep.text.toString(),
                        address = binding.editFieldEndereco.text.toString(),
                        address_number = addressNumberTrat,
                        address_complement = binding.editFieldComplemento.text.toString(),
                        address_city = binding.editFieldCidade.text.toString(),
                        address_uf = binding.editFieldUF.text.toString()
                    )
                }
            }

            if(request?.cep?.isEmpty() == true ||
                request?.address?.isEmpty() == true ||
                request?.address_city?.isEmpty() == true ||
                request?.address_uf?.isEmpty() == true ||
                request?.address_number?.equals(0) == true
            ){
                Snackbar.make(binding.view7, "Favor preencher todos os campos.", Snackbar.LENGTH_LONG).show()
            } else {
                Log.i("request payload", request.toString())
                criarPedido(request, container)
            }
        }

        binding.btnBuscarCep.setOnClickListener {
            if(cep.length() != 8){
                Snackbar.make(binding.view7, "CEP Inválido", Snackbar.LENGTH_LONG).show()
            } else {
                buscarCep(cep.text.toString())
            }
        }

        return binding.root
    }

    private fun buscarCep(cep: String?) {
        val callback = object : Callback<ViaCep> {
            override fun onResponse(call: Call<ViaCep>, response: Response<ViaCep>) {
                if(response.isSuccessful) {
                    val dadosEndereco = response.body()

                    Log.println(Log.INFO, "INFO", dadosEndereco.toString())

                    binding.editFieldEndereco.setText(dadosEndereco?.logradouro)
                    binding.editFieldCidade.setText(dadosEndereco?.localidade)
                    binding.editFieldUF.setText(dadosEndereco?.uf)

                } else {
                    // val error = response.errorBody().toString()
                    Snackbar.make(binding.view7, "Não é possível encontrar o CEP informado.",
                        Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ViaCep>, t: Throwable) {
                Snackbar.make(binding.view7, "Não foi possível executar o serviço de CEP.",
                    Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar o serviço.", t)
            }
        }

        if (cep != null) {
            ViaCepAPI.cep.getCep(cep).enqueue(callback)
        }
    }

    private fun criarPedido(request: OrderRequest?, container: ViewGroup?) {
        val service = retrofit.create(OrderService::class.java)
        val call = request?.let { service.criarPedido(it) }
        val callback = object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>){
                if(response.isSuccessful){
                    val orderId = response.body()?.order_id
                    Log.i("callback", response.body().toString())

                    val bundle = Bundle()
                    orderId?.let { bundle.putInt("orderId", it) }

                    val fragment = PurchaseFragment.newInstance()
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