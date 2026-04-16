package com.example.labtask_10_universityeventmanagementapp

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SeatAdapter(private val total:Int, private val booked:Set<Int>,
                  private val selected: MutableSet<Int>,private val onChange:()-> Unit):
    BaseAdapter() {

    override fun getCount(): Int =total
    override fun getItem(position: Int): Any =position
    override fun getItemId(position: Int): Long =position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{

        val tv = TextView(parent?.context)
        tv.layoutParams = ViewGroup.LayoutParams(100,100)
        tv.text = (position+1).toString()
        tv.gravity = Gravity.CENTER

        when{
            booked.contains(position) -> tv.setBackgroundColor(Color.RED)
            selected.contains(position) -> tv.setBackgroundColor(Color.BLUE)
            else -> tv.setBackgroundColor(Color.GREEN)
        }

        tv.setOnClickListener {
            if(!booked.contains(position)){
                if(selected.contains(position)){
                    selected.remove(position)
                }else{
                    selected.add(position)
                }
                notifyDataSetChanged()
                onChange()
            }
        }

        return tv

    }
}