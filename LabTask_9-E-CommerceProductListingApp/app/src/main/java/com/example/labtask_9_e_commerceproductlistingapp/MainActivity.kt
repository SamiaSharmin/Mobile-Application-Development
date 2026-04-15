package com.example.labtask_9_e_commerceproductlistingapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProductAdapter
    lateinit var tvCartCount: TextView
    lateinit var btnToogle: ImageView
    lateinit var btnCart: ImageView
    lateinit var etSearch: EditText
    lateinit var categoryContainer :  LinearLayout
    lateinit var emptyState: LinearLayout

    var productList = mutableListOf<Product>()
    var allProducts = mutableListOf<Product>()
    var currentCategory = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        tvCartCount = findViewById<TextView>(R.id.tvCartCount)
        btnToogle = findViewById<ImageView>(R.id.btnToggle)
        btnCart = findViewById<ImageView>(R.id.btnCart)
        etSearch = findViewById<EditText>(R.id.etSearch)
        emptyState = findViewById<LinearLayout>(R.id.emptyState)
        categoryContainer = findViewById<LinearLayout>(R.id.categoryContainer)

        loadProduct()
        allProducts = productList.toMutableList()

        adapter = ProductAdapter(this, productList){ count ->
            tvCartCount.text = count.toString()
        }
        adapter.setFullList(productList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        setupCategoryFilter()
        updateCategoryButtons("All")
        currentCategory = "All"
        filterProducts()

        btnToogle.setOnClickListener {
            adapter.toggleView()
            if(adapter.isGridMode){
                recyclerView.layoutManager = GridLayoutManager(this,2)
            }else{
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
        }

        etSearch.addTextChangedListener(object :android.text.TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts()
            }
        })

        btnCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadProduct(){
        productList = mutableListOf(
            Product(1,"Wireless Bluetooth5.2 Earphone", 700.0,4.5f,"Electronics",R.drawable.electronics1),
            Product(2, "Dress1", 80000.0, 4.5f, "Clothing", R.drawable.clothing1),
            Product(3, "Dress2", 1200.0, 4.0f, "Clothing", R.drawable.clothing2),
            Product(4, "The Art of Being ALONE", 500.0, 4.0f, "Books", R.drawable.book1),
            Product(5, "Kishwan Horlicks Biscuit", 200.0, 4.2f, "Food", R.drawable.food1),
            Product(6, "Toys for kids", 300.0, 4.1f, "Toys", R.drawable.toy1)
        )
    }

    private fun toggleEmpty() {
        if (adapter.itemCount == 0) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupCategoryFilter() {
        val container = findViewById<LinearLayout>(R.id.categoryContainer)

        for (i in 0 until container.childCount) {
            val btn = container.getChildAt(i) as Button

            btn.setOnClickListener {
                currentCategory = btn.text.toString()
                updateCategoryButtons(currentCategory)
                filterProducts()
            }
        }
    }

    private fun updateCategoryButtons(selectedCategory: String){
        for (i in 0 until categoryContainer.childCount) {
            val btn = categoryContainer.getChildAt(i) as Button
            if (btn.text.toString() == selectedCategory) {
                btn.setBackgroundColor(android.graphics.Color.parseColor("#D94572"))
            } else {
                btn.setBackgroundColor(android.graphics.Color.LTGRAY)
            }
        }
    }

    private fun filterProducts() {

        val searchText = etSearch.text.toString().lowercase()

        val filteredList = allProducts.filter {
            val matchCategory = currentCategory == "All" || it.category == currentCategory
            val matchSearch = it.name.lowercase().contains(searchText)
            matchCategory && matchSearch
        }

        adapter.updateList(filteredList.toMutableList())
        toggleEmpty()
    }
}