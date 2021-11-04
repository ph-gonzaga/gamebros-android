package br.senac.gamebros.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.R
import br.senac.gamebros.adapter.AdapterProduct
import br.senac.gamebros.databinding.FragmentListProductBinding
import br.senac.gamebros.model.Product


class ListProductFragment : Fragment() {

    lateinit var binding: FragmentListProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListProductBinding.inflate(inflater)

        val recycleViewProduct = binding.recycleListProducts
        recycleViewProduct.layoutManager = LinearLayoutManager(context)
        recycleViewProduct .setHasFixedSize(true)

        //Configura o adapter
        val listaProdutos: MutableList<Product> = mutableListOf()
        val adapterProdutos = AdapterProduct(listaProdutos)
        recycleViewProduct.adapter = adapterProdutos

        var product1 = Product(
            R.drawable.zelda_switch,
            "Nintendo Switch",
            "Jogos",
            "Zelda: Breath of the Wild",
            "R\$ 330,00"
        )

        listaProdutos.add(product1)

        var product2 = Product(
            R.drawable.mario_party_switch,
            "Nintendo Switch",
            "Jogos",
            "Mario Party",
            "R\$ 320,00"
        )

        listaProdutos.add(product2)

        var product3 = Product(
            R.drawable.pokemon_snap_switch,
            "Nintendo Switch",
            "Jogos",
            "Pokemon Snap",
            "R\$ 320,00"
        )

        listaProdutos.add(product3)

        var product4 = Product(
            R.drawable.smash_switch,
            "Nintendo Switch",
            "Jogos",
            "Super Smash Bros Ultimate Fighters",
            "R\$ 300,00"
        )

        listaProdutos.add(product4)

        var product5 = Product(
            R.drawable.kirby_switch,
            "Nintendo Switch",
            "Jogos",
            "Kirby Star Allies",
            "R\$ 315,00"
        )

        listaProdutos.add(product5)

        return binding.root

    }


    companion object {
        @JvmStatic
        fun newInstance() = ListProductFragment()
    }
}