package com.example.labtask_9_e_commerceproductlistingapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var adapter: CartAdapter
    var cartList = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerCart)
        tvTotal = findViewById<TextView>(R.id.tvTotal)
        cartList = CartManager.cartItems

        adapter = CartAdapter(cartList){
            updateTotal()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        updateTotal()

    }

    fun updateTotal(){
        val total = cartList.sumOf { it.price }
        tvTotal.text = "Total: Tk $total"
    }
}