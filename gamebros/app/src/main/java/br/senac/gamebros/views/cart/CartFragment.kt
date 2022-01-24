package br.senac.gamebros.views.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.R
import br.senac.gamebros.adapter.AdapterCart
import br.senac.gamebros.views.checkout.AddressCheckoutFragment
import br.senac.gamebros.databinding.FragmentCartBinding
import br.senac.gamebros.model.Cart

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater)

        val recycleViewCart = binding.recyclerCartProducts
        recycleViewCart.layoutManager = LinearLayoutManager(context)
        recycleViewCart.setHasFixedSize(true)

        //Configura o adapter

        val listaItensSacola: MutableList<Cart> = mutableListOf()
        val adapterCart = AdapterCart(listaItensSacola)
        recycleViewCart.adapter = adapterCart

        var itemSacola = Cart(
            R.drawable.mario_party_switch,
            "Nintendo Switch",
            "Jogos",
            "Super Mario Bros.U Deluxe",
            "R\$ 330,00"
        )

        listaItensSacola.add(itemSacola)

        binding.btnCheckoutEndereco.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    AddressCheckoutFragment.newInstance()
                ).addToBackStack("fragAddressCheckout").commit()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}