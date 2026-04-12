package com.example.labtask_7_photogalleryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.BaseAdapter

class PhotoAdapter(private val context: Context, private var photoList: MutableList<Photo>):
    BaseAdapter() {
        var selectionMode = false
    override fun getCount(): Int = photoList.size
    override fun getItem(position: Int): Any = photoList[position]
    override fun getItemId(position: Int): Long = photoList[position].id.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false)
        val photo = photoList[position]
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val img = view.findViewById<ImageView>(R.id.imagePhoto)
        val checkBox = view.findViewById<CheckBox>(R.id.checkSelect)

        img.setImageResource(photo.resourceId)
        title.text = photo.title

        if(selectionMode){
            checkBox.visibility = View.VISIBLE
            checkBox.isChecked = photo.isSelected
        }else{
            checkBox.visibility = View.GONE
        }
        return view
    }

    fun updateList(newList: MutableList<Photo>){
        photoList = newList
        notifyDataSetChanged()
    }

    fun getSelectedCount(): Int{
        return photoList.count(){it.isSelected}
    }

    fun deleteSelected(){
        photoList.removeAll{it.isSelected}
        notifyDataSetChanged()
    }

    fun getSelectedPhotos(): List<Photo>{
        return photoList.filter { it.isSelected }
    }
}