package br.senac.gamebros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.views.adapter.AdapterProduct
import br.senac.gamebros.databinding.ActivityListProductBinding

import br.senac.gamebros.model.Product

class ListProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycleViewProduct = binding.listProducts
        recycleViewProduct.layoutManager = LinearLayoutManager(this)

        recycleViewProduct .setHasFixedSize(true)
        //Configura o adapter
        val listaProdutos: MutableList<Product> = mutableListOf()
        val adapterProdutos = AdapterProduct(listaProdutos)
        recycleViewProduct.adapter = adapterProdutos

        var product1 = Product(
            R.drawable.zelda_switch,
            "Nintendo S",
            "Jogos",
            "Zelda",
            "R\$ 350,00"
        )

        listaProdutos.add(product1)

        var product2 = Product(
            R.drawable.mario_party_switch,
            "Nintendo S",
            "Jogos",
            "Zelda",
            "R\$ 350,00"
        )

        listaProdutos.add(product2)

        var product3 = Product(
            R.drawable.pokemon_evee_switch,
            "Nintendo S",
            "Jogos",
            "Zelda",
            "R\$ 350,00"
        )

        listaProdutos.add(product3)

        var product4 = Product(
            R.drawable.smash_switch,
            "Nintendo S",
            "Jogos",
            "Zelda",
            "R\$ 350,00"
        )

        listaProdutos.add(product4)

        var product5 = Product(
            R.drawable.kirby_switch,
            "Nintendo S",
            "Jogos",
            "Zelda",
            "R\$ 350,00"
        )

        listaProdutos.add(product5)
    }
}