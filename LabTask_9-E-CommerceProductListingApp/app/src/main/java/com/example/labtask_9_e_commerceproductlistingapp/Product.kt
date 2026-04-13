package com.example.labtask_9_e_commerceproductlistingapp

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Float,
    val category: String,
    val imageRes: Int,
    var inCart: Boolean = false
)