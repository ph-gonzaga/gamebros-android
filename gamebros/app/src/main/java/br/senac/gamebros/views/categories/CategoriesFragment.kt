package br.senac.gamebros.views.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.senac.gamebros.databinding.FragmentCategoriesBinding
import br.senac.gamebros.model.Category
import br.senac.gamebros.views.products.ListProductFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)

        binding.btnCategorieProduct.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    ListProductFragment.newInstance()
                ).addToBackStack("fragProduct").commit()
            }
        }

        val categories = buscarCategorias()

        categories.forEach {
            val container = binding.container
            var btn = Button(context)
            container.addView(btn)
        }

        return binding.root

    }

    fun buscarCategorias() {
        val callback = object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if(response.isSuccessful) {
                    val data = response.body()

                    Log.println(Log.INFO, "INFO", data.toString())

//                    binding.editFieldEndereco.setText(dadosEndereco?.logradouro)
//                    binding.editFieldCidade.setText(dadosEndereco?.localidade)
//                    binding.editFieldUF.setText(dadosEndereco?.uf)

                } else {
                    // val error = response.errorBody().toString()
                    Snackbar.make(binding.container, "Não é possível encontrar o CEP informado.",
                        Snackbar.LENGTH_LONG).show()

                    Log.e("ERROR", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Snackbar.make(binding.container, "Não foi possível executar o serviço de CEP.",
                    Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar o serviço.", t)
            }
        }

//        API.categorias.listarCategorias().enqueue(callback)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}

private fun Unit.forEach(function: () -> Unit) {

}
