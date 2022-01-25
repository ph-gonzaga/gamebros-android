package br.senac.gamebros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.R
import br.senac.gamebros.model.Order
import br.senac.gamebros.model.OrderRequest
import br.senac.gamebros.model.OrdersListResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapter(private val order: ArrayList<OrdersListResponse>): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Cria o item da lista
        val itemLista = LayoutInflater.from(parent?.context).inflate(R.layout.item_order, parent, false)
        val holder = OrderViewHolder(itemLista)
        return holder
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Exibe o item da lista
        val date = Calendar.getInstance().time
        var dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        holder.pedido.text = "#"+("00000000" + order[position].id.toString()).takeLast(4)
        holder.data.text = dateTimeFormat.format(date).toString()
        holder.status.text = "Entregue"
    }

    override fun getItemCount(): Int = order.size


    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pedido = itemView.findViewById<TextView>(R.id.labelNumeroPedidoItem)
        val data = itemView.findViewById<TextView>(R.id.labelDataPedidoItem)
        val status = itemView.findViewById<TextView>(R.id.labelStatusPedidoItem)

    }
}