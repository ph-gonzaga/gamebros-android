package br.senac.gamebros.views.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.adapter.OrderAdapter
import br.senac.gamebros.databinding.FragmentListOrdersBinding
import br.senac.gamebros.model.CartProductsResponse
import br.senac.gamebros.model.Order
import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrdersListResponse


class OrderListFragment : Fragment() {
    lateinit var binding: FragmentListOrdersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListOrdersBinding.inflate(inflater)

        val bundle = arguments
        val data = bundle?.getSerializable("data") as ArrayList<OrdersListResponse>

        val recyclerViewOrder = binding.recyclerListOrders
        recyclerViewOrder.layoutManager = LinearLayoutManager(context)
        recyclerViewOrder.setHasFixedSize(true)

        //Configura o adapter
        val listaPedidos = ArrayList<OrdersListResponse>()
        val adapterPedidos = OrderAdapter(listaPedidos)
        recyclerViewOrder.adapter = adapterPedidos

        data.forEach {
            listaPedidos.add(it)
        }

        return binding.root

    }


    companion object {
        @JvmStatic
        fun newInstance() = OrderListFragment()
    }
}