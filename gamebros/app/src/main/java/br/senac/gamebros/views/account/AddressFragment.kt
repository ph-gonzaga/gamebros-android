package br.senac.gamebros.views.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.databinding.FragmentAddressBinding
import br.senac.gamebros.model.ViaCep
import br.senac.gamebros.services.ViaCepService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressFragment : Fragment() {
    lateinit var binding : FragmentAddressBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddressBinding.inflate(inflater)

        binding.btnBuscarCep.setOnClickListener {
            if(binding.editFieldCEP.length() != 8){
                Snackbar.make(binding.container, "CEP Inválido", Snackbar.LENGTH_LONG).show()
            } else {
                val cep = binding.editFieldCEP.text.toString()
                buscarEndereco(cep)
            }
        }

        binding.btnPesquisarProduto.setOnClickListener {
            Snackbar.make(binding.container, "Endereço atualizado com sucesso!", Snackbar.LENGTH_LONG).show()
        }

        return binding.root
    }

    fun buscarEndereco(cep: String) {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ViaCepService::class.java)
        val call = service.getCep(cep)
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
                    Snackbar.make(binding.container, "Não é possível encontrar o CEP informado.",
                        Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ViaCep>, t: Throwable) {
                Snackbar.make(binding.container, "Não foi possível executar o serviço de CEP.",
                    Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar o serviço.", t)
            }
        }

        call.enqueue(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddressFragment()
    }

}