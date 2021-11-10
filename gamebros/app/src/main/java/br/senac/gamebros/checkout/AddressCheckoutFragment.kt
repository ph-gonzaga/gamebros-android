package br.senac.gamebros.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.R
import br.senac.gamebros.cart.CartEmptyFragment
import br.senac.gamebros.categories.CategoriesFragment
import br.senac.gamebros.databinding.FragmentAddressCheckoutBinding
import br.senac.gamebros.databinding.FragmentCartEmptyBinding

class AddressCheckoutFragment : Fragment() {

    lateinit var binding: FragmentAddressCheckoutBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddressCheckoutBinding.inflate(inflater)

        binding.btnFinalizarCompra.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    PurchaseFragment.newInstance()
                ).addToBackStack("fragCheckout").commit()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddressCheckoutFragment()
    }
}