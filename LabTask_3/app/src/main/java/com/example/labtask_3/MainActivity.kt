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
    lateinit var caloriesText: TextView
    lateinit var waterText: TextView
    lateinit var workoutText: TextView
    lateinit var percentText : TextView
    lateinit var progressBar : ProgressBar
    lateinit var quoteText : TextView
    lateinit var btnUpdate : Button
    lateinit var cardSteps : androidx.cardview.widget.CardView
    lateinit var cardCalories : androidx.cardview.widget.CardView
    lateinit var cardWater : androidx.cardview.widget.CardView
    lateinit var cardWorkout : androidx.cardview.widget.CardView

    var currentSteps= 3500
    var currentCalories = 450
    var currentWater = 1.5
    var currentWorkout = 30
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
        caloriesText = findViewById(R.id.tvCaloriesValue)
        waterText = findViewById(R.id.tvWaterValue)
        workoutText = findViewById(R.id.tvWorkoutValue)

        percentText = findViewById(R.id.tvPercent)
        progressBar =findViewById(R.id.progressSteps)
        quoteText = findViewById(R.id.tvQuote)
        btnUpdate = findViewById(R.id.btnUpdate)

        updateProgress()

        btnUpdate.setOnClickListener {
            showUpdateOptions()
        }

    }

    private fun showUpdateOptions() {

        val options = arrayOf("Steps", "Calories", "Water", "Workout")

        AlertDialog.Builder(this)
            .setTitle("Update Fitness Data")
            .setItems(options) { _, which ->

                when (which) {
                    0 -> showInputDialog("Steps")
                    1 -> showInputDialog("Calories")
                    2 -> showInputDialog("Water (Liters)")
                    3 -> showInputDialog("Workout (Minutes)")
                }

            }
            .show()
    }

    private fun showInputDialog(type: String) {

        val view = layoutInflater.inflate(R.layout.dialog_input, null)

        val input = view.findViewById<EditText>(R.id.inputValue)
        val title = view.findViewById<TextView>(R.id.dialogTitle)

        title.text = "Update $type"
        input.hint = "Enter $type"

        AlertDialog.Builder(this).setView(view).setPositiveButton("Update") { _, _ ->

            val value = input.text.toString()
            if (value.isNotEmpty()) {
                when (type) {

                    "Steps" -> {
                        currentSteps = value.toInt()
                        stepsText.text = currentSteps.toString()
                        updateProgress()
                    }

                    "Calories" -> {
                        currentCalories = value.toInt()
                        caloriesText.text = "$currentCalories"
                    }

                    "Water (Liters)" -> {
                        currentWater = value.toDouble()
                        waterText.text = "$currentWater L"
                    }

                    "Workout (Minutes)" -> {
                        currentWorkout = value.toInt()
                        workoutText.text = "$currentWorkout min"
                    }
                }
                changeQuote()
            }

        }.setNegativeButton("Cancel", null).show()
    }

    private fun updateProgress() {

        val percent = (currentSteps * 100) / goalSteps
        progressBar.progress = percent
        percentText.text = "$percent%"
    }

    private fun changeQuote() {

        val quotes = listOf(
            "Stay active, stay healthy!",
            "Every step counts!",
            "Push yourself today!",
            "Small progress is still progress!",
            "Your only limit is you!"
        )

        quoteText.text = quotes.random()
    }

}