package com.example.labtask_6_newsarticlereaderapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.SectionIndexer
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private lateinit var scrollView: NestedScrollView
    private lateinit var btnIntro : Button
    private lateinit var btnKey : Button
    private lateinit var btnAnalysis : Button
    private lateinit var btnConclusion : Button
    private lateinit var sectionIntro : TextView
    private lateinit var sectionKey : TextView
    private lateinit var sectionAnalysis: TextView
    private lateinit var sectionConclusion : TextView
    private lateinit var btnTop : ImageButton
    private lateinit var btnBookmark : ImageView
    private lateinit var btnShare : ImageView

    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scrollView = findViewById(R.id.scrollView)
        btnIntro = findViewById<Button>(R.id.btnIntro)
        btnKey = findViewById<Button>(R.id.btnKey)
        btnAnalysis = findViewById<Button>(R.id.btnAnalysis)
        btnConclusion = findViewById<Button>(R.id.btnConclusion)
        sectionIntro = findViewById<TextView>(R.id.sectionIntro)
        sectionKey = findViewById<TextView>(R.id.sectionKey)
        sectionAnalysis = findViewById<TextView>(R.id.sectionAnalysis)
        sectionConclusion = findViewById<TextView>(R.id.sectionConclusion)
        btnTop = findViewById< ImageButton>(R.id.btnTop)
        btnShare = findViewById<ImageButton>(R.id.btnShare)
        btnBookmark = findViewById<ImageButton>(R.id.btnBookmark)

        btnIntro.setOnClickListener {
            scrollToView(sectionIntro)
        }
        btnKey.setOnClickListener {
            scrollToView(sectionKey)
        }
        btnAnalysis.setOnClickListener {
            scrollToView(sectionAnalysis)
        }
        btnConclusion.setOnClickListener {
            scrollToView(sectionConclusion)
        }

        btnTop.setOnClickListener {
            scrollView.smoothScrollTo(0,0)
        }

        btnBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            if(isBookmarked){
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this,"Article Bookmarked", Toast.LENGTH_SHORT).show()
            }else{
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this,"Bookmarked Removed", Toast.LENGTH_SHORT).show()
            }
        }

        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Shared")
            startActivity(Intent.createChooser(shareIntent,"Share via"))
        }

    }

    private fun scrollToView(view: TextView){
        scrollView.post {
            scrollView.smoothScrollTo(0,view.top)
        }
    }
}