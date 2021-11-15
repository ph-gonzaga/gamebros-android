package br.senac.gamebros.views.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.views.categories.CategoriesFragment
import br.senac.gamebros.databinding.FragmentCartEmptyBinding

class CartEmptyFragment : Fragment() {

    lateinit var binding: FragmentCartEmptyBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartEmptyBinding.inflate(inflater)

        binding.btnPesquisarProduto.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    CategoriesFragment.newInstance()
                ).addToBackStack("fragCategories").commit()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartEmptyFragment()
    }
}