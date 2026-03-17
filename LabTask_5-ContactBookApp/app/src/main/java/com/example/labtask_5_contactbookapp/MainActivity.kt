package com.example.labtask_5_contactbookapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var fabAdd: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var tvEmpty: TextView
    lateinit var adapter: ContactAdapter
    var contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.listView)
        searchView = findViewById(R.id.searchView)
        fabAdd = findViewById(R.id.fabAdd)
        tvEmpty = findViewById(R.id.tvEmpty)

        adapter = ContactAdapter(this, contactList)
        listView.adapter = adapter

        updateEmptyView()

        fabAdd.setOnClickListener {
            showAddDialog()
        }

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                updateEmptyView()
                return true
            }
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)
            Toast.makeText(
                this,
                "Name: ${contact.name}\nPhone: ${contact.phone}\nEmail: ${contact.email}",
                Toast.LENGTH_LONG
            ).show()
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)

            AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete ${contact.name}?")
                .setPositiveButton("Yes") { _, _ ->
                    contactList.remove(contact)
                    adapter.updateList(contactList)
                    updateEmptyView()
                }
                .setNegativeButton("No", null).show()

            true
        }
    }

    private fun showAddDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact, null)

        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val etPhone = dialogView.findViewById<EditText>(R.id.etPhone)
        val etEmail = dialogView.findViewById<EditText>(R.id.etEmail)

        AlertDialog.Builder(this)
            .setTitle("Add Contact")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->

                val name = etName.text.toString()
                val phone = etPhone.text.toString()
                val email = etEmail.text.toString()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    contactList.add(Contact(name, phone, email))
                    adapter.updateList(contactList)
                    updateEmptyView()
                } else {
                    Toast.makeText(this, "Name & Phone required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateEmptyView() {
        if (adapter.count == 0) {
            tvEmpty.visibility = TextView.VISIBLE
        } else {
            tvEmpty.visibility = TextView.GONE
        }
    }
}