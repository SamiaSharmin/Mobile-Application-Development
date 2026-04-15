package com.example.labtask_9_e_commerceproductlistingapp


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class CartAdapter(private val list: MutableList<Product>, private val onUpdate:() -> Unit):
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val img: ImageView = view.findViewById<ImageView>(R.id.imgProduct)
        val name: TextView = view.findViewById<TextView>(R.id.tvName)
        val price: TextView = view.findViewById<TextView>(R.id.tvPrice)
        val btnRemove: Button = view.findViewById<Button>(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        holder.img.setImageResource(product.imageRes)
        holder.name.text =product.name
        holder.price.text = "Tk ${product.price}"
        holder.btnRemove.setOnClickListener {
            product.inCart = false
            list.removeAt(position)
            notifyDataSetChanged()
            onUpdate()
        }
    }
}