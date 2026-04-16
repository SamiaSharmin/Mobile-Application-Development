package com.example.labtask_10_universityeventmanagementapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventsActivity : AppCompatActivity() {

    lateinit var adapter: EventAdapter
    lateinit var recycler: RecyclerView
    lateinit var etSearch: EditText
    lateinit var categoryContainer: LinearLayout
    var eventList = mutableListOf<Event>()
    var allEvents = mutableListOf<Event>()
    var currentCategory = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_events)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recycler = findViewById<RecyclerView>(R.id.recyclerEvents)
        etSearch = findViewById<EditText>(R.id.etSearch)
        categoryContainer = findViewById<LinearLayout>(R.id.categoryContainer)

        loadEvents()
        allEvents = eventList.toMutableList()
        adapter = EventAdapter(eventList) {
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(etSearch.text.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        for(i in 0 until categoryContainer.childCount){
            val btn = categoryContainer.getChildAt(i) as Button
            btn.setOnClickListener {
                currentCategory = btn.text.toString()
                filter(etSearch.text.toString())
            }
        }
    }

    fun filter(query:String){
        val filtered = allEvents.filter {
            val matchCategory = currentCategory == "All"|| it.category == currentCategory
            val matchSearch = it.title.lowercase().contains(query.lowercase())
            matchCategory && matchSearch
        }
        adapter.updateList(filtered.toMutableList())
    }

    fun loadEvents(){
        eventList = mutableListOf(
            Event(1,"Tecno","17 May 2026","10AM","AIUB Multipurpose hall 1", "Tech","Event1",150.0, 50,45,R.drawable.event1),
            Event(1,"Tecno","22 May 2026","10AM","AIUB Multipurpose hall 1", "Cultural","Event2",150.0, 50,35,R.drawable.event1),
            Event(1,"Seminar","19 May 2026","9AM","AIUB Multipurpose hall 2", "Academic","Event3",150.0, 150,45,R.drawable.event1),
            Event(1,"Football match","16 May 2026","11AM","Footbal field", "Sports","Event4",150.0, 50,15,R.drawable.event1),
            Event(1,"Tecno","02 May 2026","10AM","AIUB Multipurpose hall 2", "Cultural","Event5",150.0, 50,45,R.drawable.event1)
        )
    }

}