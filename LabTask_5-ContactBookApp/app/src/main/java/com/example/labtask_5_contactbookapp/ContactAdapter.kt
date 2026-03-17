package com.example.labtask_5_contactbookapp

import android.widget.ArrayAdapter
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.*
import java.util.*

class ContactAdapter(private val context: Context,private var contactList: MutableList<Contact>):
    ArrayAdapter<Contact>(context,0,contactList) {

    private var filteredList: MutableList<Contact> = contactList.toMutableList()

    override fun getCount(): Int {
        return filteredList.size
    }

    override fun getItem(position: Int): Contact {
        return filteredList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = getItem(position)

        val tvInitial = view.findViewById<TextView>(R.id.tvInitial)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)
        val imgCall = view.findViewById<ImageView>(R.id.imgCall)

        tvInitial.text = contact.getInitial()
        tvName.text = contact.name
        tvPhone.text = contact.phone

        val colors = listOf(
            "#547792", "#213448", "#94B4C1", "#7A9E9F", "#B76E79"
        )
        val randomColor = Color.parseColor(colors[position % colors.size])
        tvInitial.setBackgroundColor(randomColor)

        imgCall.setOnClickListener {
            Toast.makeText(context, "Calling ${contact.phone}", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {

            contactList.toMutableList()
        } else {
            contactList.filter {
                it.name.lowercase(Locale.getDefault())
                    .contains(query.lowercase(Locale.getDefault()))
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun updateList(newList: MutableList<Contact>) {
        contactList = newList
        filteredList = newList.toMutableList()
        notifyDataSetChanged()
    }

}