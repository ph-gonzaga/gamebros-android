package br.senac.gamebros.views.checkout

import android.os.Bundle
import android.util.Log
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

        val bundle = arguments
        val orderId = bundle?.getInt("orderId")
        Log.i("orderID", orderId.toString())

        binding.textPedidoNumero.text = "#"+("00000000" + orderId.toString()).takeLast(4)

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