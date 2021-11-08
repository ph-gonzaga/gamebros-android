package br.senac.gamebros.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.R
import br.senac.gamebros.databinding.FragmentCategoriesBinding
import br.senac.gamebros.products.ProductFragment

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)

        binding.btnCategorieProduct.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    ProductFragment.newInstance()
                ).addToBackStack("fragProduct").commit()
            }
        }

        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}