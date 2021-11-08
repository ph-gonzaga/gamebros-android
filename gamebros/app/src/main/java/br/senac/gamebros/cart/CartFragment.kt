package br.senac.gamebros.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.R
import br.senac.gamebros.adapter.AdapterProduct
import br.senac.gamebros.databinding.FragmentCartBinding
import br.senac.gamebros.databinding.FragmentListProductBinding
import br.senac.gamebros.model.Product
import br.senac.gamebros.products.ListProductFragment

class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater)

        val recycleViewCart = binding.recycleCartProducts
        recycleViewCart.layoutManager = LinearLayoutManager(context)
        recycleViewCart.setHasFixedSize(true)

        //Configura o adapter

//        val listaProdutos: MutableList<Product> = mutableListOf()
//        val adapterProdutos = AdapterProduct(listaProdutos)
//        recycleViewProduct.adapter = adapterProdutos
//
//        var product1 = Product(
//            R.drawable.zelda_switch,
//            "Nintendo Switch",
//            "Jogos",
//            "Zelda: Breath of the Wild",
//            "R\$ 330,00"
//        )
//
//        listaProdutos.add(product1)
//
        return binding.root

    }


    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}