package com.example.labtask_4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayout
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var tableLayout: TableLayout
    lateinit var subjectTB: EditText
    lateinit var obtainedTB: EditText
    lateinit var totalTB: EditText
    lateinit var btnAdd: Button
    lateinit var btnShare: Button
    lateinit var tvCGPA: TextView

    var  totalSubjects = 0
    var passed = 0
    var failed = 0
    var totalCGPA = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tableLayout = findViewById(R.id.gradeTable)
        subjectTB = findViewById(R.id.subjectTB)
        obtainedTB = findViewById(R.id.obtainedTB)
        totalTB = findViewById(R.id.totalTB)
        tvCGPA = findViewById(R.id.tvCGPA)
        btnAdd = findViewById(R.id.btnAdd)
        btnShare = findViewById(R.id.btnShare)

        btnAdd.setOnClickListener {
            addSubject()
        }

        btnShare.setOnClickListener {
            shareReport()
        }

    }

    private fun addSubject() {

        val subject = subjectTB.text.toString()
        val obtainedStr = obtainedTB.text.toString()
        val totalStr = totalTB.text.toString()

        if (subject.isEmpty() || obtainedStr.isEmpty() || totalStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val obtained = obtainedStr.toIntOrNull()
        val total = totalStr.toIntOrNull()

        if (obtained == null || total == null) {
            Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        if (total == 0) {
            Toast.makeText(this, "Total marks cannot be 0", Toast.LENGTH_SHORT).show()
            return
        }

        val percentage = (obtained * 100) / total

        val grade = calculateGrade(percentage)
        val cgpa = calculateGPA(grade)

        totalSubjects++
        totalCGPA += cgpa

        if (grade == "F") {
            failed++
        } else {
            passed++
        }

        addRow(subject, obtained, total, grade)

        updateGPA()
        updateSummaryRow()

        clearInputs()
    }

    private fun calculateGrade(percent: Int): String {

        return when (percent) {
            in 90..100 -> "A+"
            in 80..89 -> "A"
            in 70..79 -> "B+"
            in 60..69 -> "B"
            in 50..59 -> "C"
            in 40..49 -> "D"
            else -> "F"
        }
    }

    private fun calculateGPA(grade: String): Double {

        return when (grade) {
            "A+" -> 4.0
            "A" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "C" -> 2.0
            "D" -> 1.0
            else -> 0.0
        }
    }

    private fun addRow(subject: String, obtained: Int, total: Int, grade: String) {

        val row = TableRow(this)

        val tvSubject = TextView(this)
        val tvObtained = TextView(this)
        val tvTotal = TextView(this)
        val tvGrade = TextView(this)

        tvSubject.text = subject
        tvObtained.text = obtained.toString()
        tvTotal.text = total.toString()
        tvGrade.text = grade

        val textSize = 16f
        val textColor = getColor(android.R.color.black)

        tvSubject.textSize = textSize
        tvObtained.textSize = textSize
        tvTotal.textSize = textSize
        tvGrade.textSize = textSize

        tvSubject.setTextColor(textColor)
        tvObtained.setTextColor(textColor)
        tvTotal.setTextColor(textColor)
        tvGrade.setTextColor(textColor)

        tvSubject.setPadding(16, 16, 16, 16)
        tvObtained.setPadding(16, 16, 16, 16)
        tvTotal.setPadding(16, 16, 16, 16)
        tvGrade.setPadding(16, 16, 16, 16)

        row.addView(tvSubject)
        row.addView(tvObtained)
        row.addView(tvTotal)
        row.addView(tvGrade)

        if (grade == "F") {
            row.setBackgroundColor( androidx.core.content.ContextCompat.getColor(this, android.R.color.holo_red_light))
        } else {
            row.setBackgroundColor( androidx.core.content.ContextCompat.getColor(this, android.R.color.holo_green_light))
        }

        tableLayout.addView(row,tableLayout.childCount - 1)
    }

    private fun updateGPA() {

        val gpa = totalCGPA / totalSubjects
        tvCGPA.text = "GPA: %.2f".format(gpa)
    }

    private fun updateSummaryRow() {

        val summaryRow = tableLayout.getChildAt(tableLayout.childCount - 1) as TableRow

        val passedTV = summaryRow.getChildAt(1) as TextView
        val failedTV = summaryRow.getChildAt(2) as TextView

        passedTV.text = "Passed: $passed"
        failedTV.text = "Failed: $failed"
    }

    private fun clearInputs() {

        subjectTB.text.clear()
        obtainedTB.text.clear()
        totalTB.text.clear()
    }

    private fun shareReport() {

        val shareText = """
            Student Grade Report
            
            Total Subjects: $totalSubjects
            Passed: $passed
            Failed: $failed
            
            ${tvCGPA.text}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareText)

        startActivity(Intent.createChooser(intent, "Share Report"))
    }

    }