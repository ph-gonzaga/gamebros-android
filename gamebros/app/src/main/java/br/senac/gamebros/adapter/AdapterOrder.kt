package br.senac.gamebros.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.senac.gamebros.R
import br.senac.gamebros.model.Order

class AdapterOrder(private val order: MutableList<Order>): RecyclerView.Adapter<AdapterOrder.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Cria o item da lista
        val itemLista = LayoutInflater.from(parent?.context).inflate(R.layout.item_order, parent, false)
        val holder = OrderViewHolder(itemLista)
        return holder
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        // Exibe o item da lista
        holder.pedido.text = order[position].pedido
        holder.data.text = order[position].data
        holder.status.text = order[position].status
    }

    override fun getItemCount(): Int = order.size


    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pedido = itemView.findViewById<TextView>(R.id.labelNumeroPedidoItem)
        val data = itemView.findViewById<TextView>(R.id.labelDataPedidoItem)
        val status = itemView.findViewById<TextView>(R.id.labelStatusPedidoItem)

    }
}