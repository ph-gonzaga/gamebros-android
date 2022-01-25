package br.senac.gamebros.views.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.R
import br.senac.gamebros.adapter.CartAdapter
import br.senac.gamebros.views.checkout.AddressCheckoutFragment
import br.senac.gamebros.databinding.FragmentCartBinding
import br.senac.gamebros.model.Cart
import br.senac.gamebros.model.CartProductsResponse

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater)

        val bundle = arguments
        val totalCart = bundle?.getFloat("totalCart")
        val data = bundle?.getSerializable("data") as ArrayList<CartProductsResponse>
        data?.let {
            Log.i("Bundle data card", it.toString())
            Log.i("Bundle data card", it.size.toString())
        }

        val recycleViewCart = binding.recyclerCartProducts
        recycleViewCart.layoutManager = LinearLayoutManager(context)
        recycleViewCart.setHasFixedSize(true)

        //Configura o adapter
        val listaItensSacola = ArrayList<CartProductsResponse>()
        val adapterCart = CartAdapter(data)
        adapterCart.getRemoveItemPosition()
        recycleViewCart.adapter = adapterCart

        if(data.size == 0){
            val fragment = CartEmptyFragment()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, fragment)
            transaction?.commit()
        } else {
            listaItensSacola.add(data[0])
        }


        binding.textCarrinhoSubtotal.text = "R$" + totalCart.toString()

        binding.btnCheckoutEndereco.setOnClickListener {
            val bundle = Bundle()

            if (totalCart != null) {
                bundle.putFloat("subtotalOrder", totalCart)
                bundle.putInt("cartId", data[0].cartId)
            }

            var fragment = AddressCheckoutFragment.newInstance()
            fragment.arguments = bundle

            container?.let {
                parentFragmentManager.beginTransaction().replace(
                    it.id,
                    fragment
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