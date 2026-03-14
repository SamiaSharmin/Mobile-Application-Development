package com.example.labtask_3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    lateinit var stepsText: TextView
    lateinit var percentText : TextView
    lateinit var progressBar : ProgressBar
    lateinit var quoteText : TextView
    lateinit var btnUpdate : Button
    lateinit var cardSteps : androidx.cardview.widget.CardView
    lateinit var cardCalories : androidx.cardview.widget.CardView
    lateinit var cardWater : androidx.cardview.widget.CardView
    lateinit var cardWorkout : androidx.cardview.widget.CardView

    var currentSteps= 3500
    val goalSteps = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        stepsText = findViewById(R.id.tvStepsValue)
        percentText = findViewById(R.id.tvPercent)
        progressBar =findViewById(R.id.progressSteps)
        quoteText = findViewById(R.id.tvQuote)
        btnUpdate = findViewById(R.id.btnUpdate)
        cardSteps =findViewById(R.id.cardSteps)
        cardCalories = findViewById(R.id.cardCalories)
        cardWater = findViewById(R.id.cardWater)
        cardWorkout = findViewById(R.id.cardWorkout)

        fun updateProgress() {

            val percent = (currentSteps * 100) / goalSteps

            progressBar.progress = percent
            percentText.text = "$percent%"
        }

        fun changeQuote() {

            val quotes = listOf(
                "Stay active, stay healthy!",
                "Every step counts!",
                "Push yourself today!",
                "Small progress is still progress!",
                "Your only limit is you!"
            )

            quoteText.text = quotes.random()
        }

        fun showInputDialogue(){
            val input = EditText(this)
            input.hint = "Enter steps"

            AlertDialog.Builder(this).setTitle("Update Steps").setView(input).setPositiveButton("Update"){
                _, _ ->
                val newSteps = input.text.toString()

                if(newSteps.isNotEmpty()){
                    currentSteps = newSteps.toInt()
                    stepsText.text = currentSteps.toString()

                    updateProgress()
                    changeQuote()
                }
            }.setNegativeButton("Cancel",null).show()
        }

        updateProgress()

        cardSteps.setOnClickListener {
            Toast.makeText(this,"Steps today:$currentSteps", Toast.LENGTH_SHORT).show()
        }

        cardCalories.setOnClickListener {
            Toast.makeText(this,"Calories burned:450", Toast.LENGTH_SHORT).show()
        }

        cardWater.setOnClickListener {
            Toast.makeText(this,"Water intake:1.5L", Toast.LENGTH_SHORT).show()
        }

        cardWorkout.setOnClickListener {
            Toast.makeText(this,"Workout time:30min", Toast.LENGTH_SHORT).show()
        }

        btnUpdate.setOnClickListener {
            showInputDialogue()
        }

    }
}