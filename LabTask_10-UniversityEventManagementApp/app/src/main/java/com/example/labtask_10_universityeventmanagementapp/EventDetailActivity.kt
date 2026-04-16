package com.example.labtask_10_universityeventmanagementapp

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val event = intent.getSerializableExtra("event") as Event
        findViewById<TextView>(R.id.tvTitle).text = event.title
        findViewById<TextView>(R.id.tvDate).text = event.date
        findViewById<TextView>(R.id.tvVenue).text = event.venue
        findViewById<TextView>(R.id.tvDescription).text = event.description

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val intent = Intent(this, SeatBookingActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }



    }
}