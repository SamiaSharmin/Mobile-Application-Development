package com.example.labtask_10_universityeventmanagementapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections.list

class EventAdapter(private var eventList: MutableList<Event>, private val onClick:(Event)->Unit) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgEvent)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val date: TextView = view.findViewById(R.id.tvDate)
        val venue: TextView = view.findViewById(R.id.tvVenue)
        val seats: TextView = view.findViewById(R.id.tvSeats)
        val price: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int{
        return eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val e=eventList[position]
        holder.img.setImageResource(e.imageRes)
        holder.title.text=e.title
        holder.date.text=e.date
        holder.venue.text=e.venue
        holder.seats.text="Seats:${e.availableSeats}"
        holder.price.text="TK.${e.price}"

        holder.itemView.setOnClickListener {
            onClick(e)
        }
    }

    fun updateList(newList: MutableList<Event>){
        eventList = newList
        notifyDataSetChanged()
    }


}