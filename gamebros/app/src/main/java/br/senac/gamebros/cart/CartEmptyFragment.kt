package br.senac.gamebros.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.R
import br.senac.gamebros.home.HomeFragment

class CartEmptyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart_empty, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartEmptyFragment()
    }
}