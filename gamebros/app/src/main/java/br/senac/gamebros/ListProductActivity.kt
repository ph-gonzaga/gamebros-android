package br.senac.gamebros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ListProductActivity : AppCompatActivity() {

//    lateinit var binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_list_product)

    }
}