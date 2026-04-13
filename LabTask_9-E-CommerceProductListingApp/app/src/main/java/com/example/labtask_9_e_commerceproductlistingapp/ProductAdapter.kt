package com.example.labtask_9_e_commerceproductlistingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val context: Context, private var productList: MutableList<Product>, private val onCartChanged : (Int)-> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isGridMode = false

    companion object {
        const val VIEW_TYPE_LIST = 1
        const val VIEW_TYPE_GRID = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridMode) VIEW_TYPE_GRID else VIEW_TYPE_LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_LIST) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_product_list, parent, false)
            ListViewHolder(view)
        } else {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_product_grid, parent, false)
            GridViewHolder(view)
        }
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val product = productList[position]

        if (holder is ListViewHolder) {
            holder.name.text = product.name
            holder.price.text = "৳${product.price}"
            holder.rating.rating = product.rating
            holder.category.text = product.category
            holder.image.setImageResource(product.imageRes)

            holder.btnCart.text = if (product.inCart) "Remove" else "Add"

            holder.btnCart.setOnClickListener {
                product.inCart = !product.inCart
                notifyItemChanged(position)
                onCartChanged(getCartCount())
            }

        } else if (holder is GridViewHolder) {
            holder.name.text = product.name
            holder.price.text = "৳${product.price}"
            holder.image.setImageResource(product.imageRes)

            holder.btnCart.setOnClickListener {
                product.inCart = !product.inCart
                notifyItemChanged(position)
                onCartChanged(getCartCount())
            }
        }
    }

    private fun getCartCount(): Int {
        return productList.count { it.inCart }
    }

    fun updateList(newList: MutableList<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    fun toggleView() {
        isGridMode = !isGridMode
        notifyDataSetChanged()
    }

    fun getCartItems(): List<Product> {
        return productList.filter { it.inCart }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgProduct)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar)
        val category: TextView = itemView.findViewById(R.id.tvCategory)
        val btnCart: Button = itemView.findViewById(R.id.btnAddCart)
    }

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgProduct)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
        val btnCart: ImageButton = itemView.findViewById(R.id.btnCartIcon)
    }
}