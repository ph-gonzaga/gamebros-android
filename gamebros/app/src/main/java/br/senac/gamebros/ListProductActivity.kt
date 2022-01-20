package br.senac.gamebros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.adapter.AdapterProduct
import kotlinx.android.synthetic.main.activity_list_product.*

import br.senac.gamebros.model.Product
import kotlinx.android.synthetic.main.fragment_list_product.*

class ListProductActivity : AppCompatActivity() {

//    lateinit var binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_list_product)

    }
}