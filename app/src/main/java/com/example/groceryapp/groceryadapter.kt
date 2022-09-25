package com.example.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class groceryadapter(var list: List<GroceryItems>,
                     val groceryItemClickInterface: GroceryItemClickInterface)
    : RecyclerView.Adapter<groceryadapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV = itemView.findViewById<TextView>(R.id.item_name)
        val quantityTV = itemView.findViewById<TextView>(R.id.quantity)
        val rateTV = itemView.findViewById<TextView>(R.id.rate)
        val totalTV = itemView.findViewById<TextView>(R.id.Total_Amt)
        val deleteIV = itemView.findViewById<ImageView>(R.id.delete)
    }


    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.grocery_items, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTV.text = list.get(position).itemName
        holder.quantityTV.text = list.get(position).ItemQuantity.toString()
        holder.rateTV.text = "₹: " + list.get(position).ItemPrice.toString()
        val itemTotal: Double = list.get(position).ItemQuantity * list.get(position).ItemPrice
        holder.totalTV.text = "₹: " + itemTotal.toString()
        holder.deleteIV.setOnClickListener {
            groceryItemClickInterface.onItemClick(list.get(position))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}