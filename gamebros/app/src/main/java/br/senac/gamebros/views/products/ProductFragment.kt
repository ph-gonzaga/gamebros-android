package br.senac.gamebros.views.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.views.cart.CartFragment
import br.senac.gamebros.databinding.FragmentProductBinding


class ProductFragment : Fragment() {
    lateinit var binding: FragmentProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater)

        binding.btnComprarItem.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    CartFragment.newInstance()
                ).addToBackStack("fragProduct").commit()
            }
        }

        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}