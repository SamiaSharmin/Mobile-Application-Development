package com.example.labtask_7_photogalleryapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val img = findViewById<ImageView>(R.id.fullImage)

        val imageRes = intent.getIntExtra("image",0)
        img.setImageResource(imageRes)
    }
}