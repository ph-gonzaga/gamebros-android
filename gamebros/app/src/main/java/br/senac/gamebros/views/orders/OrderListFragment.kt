package br.senac.gamebros.views.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.senac.gamebros.views.adapter.AdapterOrder
import br.senac.gamebros.databinding.FragmentListOrdersBinding
import br.senac.gamebros.model.Order


class OrderListFragment : Fragment() {
    lateinit var binding: FragmentListOrdersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListOrdersBinding.inflate(inflater)

        val recycleViewOrder = binding.recycleListOrders
        recycleViewOrder.layoutManager = LinearLayoutManager(context)
        recycleViewOrder .setHasFixedSize(true)

        //Configura o adapter
        val listaPedidos: MutableList<Order> = mutableListOf()
        val adapterPedidos = AdapterOrder(listaPedidos)
        recycleViewOrder.adapter = adapterPedidos

        var order1 = Order(
            "10112021-1",
            "10/11/2021",
            "Entregue"
        )

        listaPedidos.add(order1)

        var order2 = Order(
            "10112021-1",
            "10/11/2021",
            "Entregue"
        )

        listaPedidos.add(order2)

        var order3 = Order(
            "10112021-1",
            "10/11/2021",
            "Entregue"
        )

        listaPedidos.add(order3)

//        var order4 = Order(
//            "10112021-1",
//            "10/11/2021",
//            "Entregue"
//        )
//
//        listaPedidos.add(order4)
//
//        var order5 = Order(
//            "10112021-1",
//            "10/11/2021",
//            "Entregue"
//        )
//
//        listaPedidos.add(order5)

        return binding.root

    }


    companion object {
        @JvmStatic
        fun newInstance() = OrderListFragment()
    }
}