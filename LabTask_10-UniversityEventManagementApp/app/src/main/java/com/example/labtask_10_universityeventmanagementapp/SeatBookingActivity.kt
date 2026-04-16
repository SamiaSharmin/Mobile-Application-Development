package com.example.labtask_10_universityeventmanagementapp

import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import android.widget.*
import kotlin.random.Random
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var grid: GridView
    private lateinit var adapter: SeatAdapter
    private lateinit var tvSummary: TextView

    private var selectedSeats = mutableSetOf<Int>()
    private var bookedSeats = mutableSetOf<Int>()
    private var price = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_booking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val event = intent.getSerializableExtra("event") as Event
        price = event.price

        grid = findViewById(R.id.gridSeats)
        tvSummary = findViewById(R.id.tvSummary)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        generateBookedSeats()

        adapter = SeatAdapter(48, bookedSeats, selectedSeats) {
            updateSummary()
        }

        grid.adapter = adapter

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Booking Confirmed!", Toast.LENGTH_SHORT).show()
        }
    }

    fun generateBookedSeats() {
        for (i in 0 until 48) {
            if (Random.nextFloat() < 0.3f) {
                bookedSeats.add(i)
            }
        }
    }

    fun updateSummary() {
        val count = selectedSeats.size
        val total = count * price
        tvSummary.text = "$count seats | Total: TK $total"
    }
}