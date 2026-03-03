package com.example.lab1_formvalidation

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var studentId : EditText
    lateinit var fullName : EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var age : EditText
    lateinit var rgGender : RadioGroup
    lateinit var rbMale : RadioButton
    lateinit var rbFemale : RadioButton
    lateinit var rbOther : RadioButton
    lateinit var cbFootball : CheckBox
    lateinit var cbCricket : CheckBox
    lateinit var cbBasketball : CheckBox
    lateinit var cbBadminton : CheckBox
    lateinit var btnDate : Button
    lateinit var btnSubmit : Button
    lateinit var btnReset: Button

    lateinit var layoutMain : ScrollView

    var selectedDate :String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        layoutMain = findViewById(R.id.main)
        studentId = findViewById(R.id.studentIdTB)
        fullName = findViewById(R.id.fullNameTB)
        email = findViewById(R.id.emailTB)
        password = findViewById(R.id.passwordTB)
        age =findViewById(R.id.ageTB)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        rbOther = findViewById(R.id.rbOther)
        cbFootball = findViewById(R.id.cbFootball)
        cbCricket = findViewById(R.id.cbCricket)
        cbBasketball = findViewById(R.id.cbBasketball)
        cbBadminton = findViewById(R.id.cbBadminton)
        btnDate = findViewById(R.id.btnDate)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnReset = findViewById(R.id.btnReset)

        btnDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,{
                    _,year,month,day ->
                    selectedDate = "$day/${month+1}/$year"
                    btnDate.text = selectedDate
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        btnSubmit.setOnClickListener {
            val id = studentId.text.toString()
            val name = fullName.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            val age = age.text.toString()

            var gender = ""
            if(rbMale.isChecked){gender = "Male"}
            else if(rbFemale.isChecked){gender = "Female"}
            else if(rbOther.isChecked){gender = "Other"}

            var sports = ""
            if(cbFootball.isChecked) sports+="Football,"
            if(cbCricket.isChecked) sports+="Cricket,"
            if(cbBasketball.isChecked) sports+="Basketball,"
            if(cbBadminton.isChecked) sports+="Badminton,"

            if(sports.endsWith(",")){
                sports = sports.substring(0,sports.length-1)

            }

            if(id.isEmpty() ||name.isEmpty() || email.isEmpty() ||password.isEmpty() ||age.isEmpty() ||gender.isEmpty() ||sports.isEmpty() ||selectedDate.isEmpty()) {

                Toast.makeText(this,"Please fill the form with all required information", Toast.LENGTH_LONG).show()
            }else if(!email.contains("@")){
                Toast.makeText(this,"Invalid email", Toast.LENGTH_LONG).show()
            }else if(age.toInt() <= 3){
                Toast.makeText(this,"Age must be greater than 3", Toast.LENGTH_LONG).show()
            }else{
                val message ="""
                    Id : $id
                    Name : $name
                    Gender : $gender
                    Sports : $sports
                    Date of birth: $selectedDate
                    """.trimIndent()
                Toast.makeText(this,message, Toast.LENGTH_LONG).show()
            }
        }

        btnReset.setOnClickListener {
            studentId.text.clear()
            fullName.text.clear()
            email.text.clear()
            password.text.clear()
            age.text.clear()
            rgGender.clearCheck()
            cbFootball.isChecked=false
            cbCricket.isChecked=false
            cbBasketball.isChecked=false
            cbBadminton.isChecked=false

            selectedDate =""
            btnDate.text = "Select Date"

            Toast.makeText(this, "Form Reset", Toast.LENGTH_SHORT).show()
        }

    }
}