package com.example.labtask_5_contactbookapp

data class Contact (val name: String, val phone: String, val email: String){
    fun getInitial(): String{
        return name.trim().first().uppercase()
    }

}