package br.senac.gamebros.views.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.senac.gamebros.databinding.FragmentPurchaseBinding
import br.senac.gamebros.views.orders.OrderListFragment

class PurchaseFragment : Fragment() {

    lateinit var binding: FragmentPurchaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPurchaseBinding.inflate(inflater)

        binding.btnAcessarPedidos.setOnClickListener {
            container?.let {
                parentFragmentManager.beginTransaction().replace(it.id,
                    OrderListFragment.newInstance()
                ).addToBackStack("fragOrders").commit()
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PurchaseFragment()
    }
}