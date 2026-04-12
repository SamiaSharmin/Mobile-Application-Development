package com.example.labtask_7_photogalleryapp

import android.content.Intent
import android.os.Bundle
import kotlin.jvm.java
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var gridView: GridView
    lateinit var adapter: PhotoAdapter
    lateinit var selectionBar: LinearLayout
    lateinit var tvSelectedCount: TextView
    lateinit var btnDelete: ImageButton
    lateinit var btnShare: ImageButton
    lateinit var fabAdd: FloatingActionButton

    var allPhotos = mutableListOf<Photo>()
    var currentList = mutableListOf<Photo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gridView = findViewById(R.id.gridView)
        selectionBar = findViewById<LinearLayout>(R.id.selectionBar)
        tvSelectedCount = findViewById<TextView>(R.id.tvSelectionCount)
        btnDelete = findViewById<ImageButton>(R.id.btnDelete)
        btnShare = findViewById<ImageButton>(R.id.btnShare)
        fabAdd = findViewById(R.id.fabAdd)

        loadPhotos()
        adapter = PhotoAdapter(this,currentList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->

            val photo = currentList[position]

            if(adapter.selectionMode){
                photo.isSelected = !photo.isSelected
                adapter.notifyDataSetChanged()
                updateSelectionUI()
            }else{
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image", photo.resourceId)
                startActivity(intent)

            }
        }

        gridView.setOnItemLongClickListener{_,_,position,_ ->
            adapter.selectionMode = true
            currentList[position].isSelected = true
            adapter.notifyDataSetChanged()
            selectionBar.visibility = LinearLayout.VISIBLE
            updateSelectionUI()
            true
        }

        btnDelete.setOnClickListener {
            val count = adapter.getSelectedCount()
            adapter.deleteSelected()
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()

            exitSelectionMode()
        }

        btnShare.setOnClickListener {
            val count = adapter.getSelectedCount()
            Toast.makeText(this,"Sharing $count photos", Toast.LENGTH_SHORT).show()
        }

        fabAdd.setOnClickListener {
            val newPhoto = Photo(
                id = allPhotos.size +1,
                resourceId = R.drawable.animal1,
                title = "Animal1",
                category = "Animals"
            )
            allPhotos.add(newPhoto)
            currentList.add(newPhoto)
            adapter.notifyDataSetChanged()
        }
        setupCategoryTabs()

    }

    private fun loadPhotos(){
        allPhotos = mutableListOf(
            Photo(1,R.drawable.nature1, "Nature1", "Nature"),
            Photo(2, R.drawable.city1,"City1","City"),
            Photo(3,R.drawable.city2,"City2","City"),
            Photo(4,R.drawable.food1,"Food1","Food"),
            Photo(5,R.drawable.flower1,"Flower1","Flower")
        )
        currentList = allPhotos.toMutableList()
    }

    private fun updateSelectionUI(){
        val count = adapter.getSelectedCount()
        tvSelectedCount.text = "$count selected"

        if(count == 0){
            exitSelectionMode()
        }
    }

    private fun exitSelectionMode(){
        adapter.selectionMode = false
        currentList.forEach { it.isSelected = false }
        selectionBar.visibility = LinearLayout.GONE
        adapter.notifyDataSetChanged()
    }

    private fun setupCategoryTabs(){
        val tabContainer = findViewById<LinearLayout>(R.id.tabContainer)

        for(i in 0 until tabContainer.childCount){
            val btn = tabContainer.getChildAt(i) as Button
            btn.setOnClickListener {
                val category = btn.text.toString()
                currentList = if (category == "All"){
                    allPhotos.toMutableList()
                }else{
                    allPhotos.filter { it.category == category }.toMutableList()
                }
                adapter.updateList(currentList)
            }
        }
    }
}