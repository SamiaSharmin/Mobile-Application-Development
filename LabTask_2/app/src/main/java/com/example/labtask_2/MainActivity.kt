package com.example.labtask_2

import android.os.Bundle
import android.os.Looper
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var layoutMain : RelativeLayout
    lateinit var imgLogo : ImageView
    lateinit var TitleTB : TextView
    lateinit var UsernameTB : EditText
    lateinit var PasswordTB : EditText
    lateinit var tvForgotPass : TextView
    lateinit var btnLogin : Button
    lateinit var profileCard : LinearLayout
    lateinit var btnLogout : Button

    lateinit var progressBar : ProgressBar

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
        imgLogo = findViewById(R.id.imgLogo)
        TitleTB = findViewById(R.id.TitleTB)
        UsernameTB = findViewById(R.id.UsernameTB)
        PasswordTB = findViewById(R.id.PasswordTB)
        tvForgotPass = findViewById(R.id.tvForgot)
        btnLogin = findViewById<Button>(R.id.btnLogin)
        profileCard = findViewById(R.id.profileCard)
        btnLogout = findViewById<Button>(R.id.btnLogout)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val username = UsernameTB.text.toString()
            val password = PasswordTB.text.toString()

            if(username == "admin" && password == "1234") {
                progressBar.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.GONE
                    imgLogo.visibility = View.GONE
                    TitleTB.visibility = View.GONE
                    UsernameTB.visibility = View.GONE
                    PasswordTB.visibility = View.GONE
                    tvForgotPass.visibility = View.GONE
                    btnLogin.visibility = View.GONE

                    profileCard.visibility = View.VISIBLE
                }, 2000)
            }
            else{
                Toast.makeText(this,"Invalid Username and Password", Toast.LENGTH_LONG).show()

            }
        }

        tvForgotPass.setOnClickListener {
            Toast.makeText(this,"Password reset link sent to your email", Toast.LENGTH_LONG).show()
        }

        btnLogout.setOnClickListener {
            profileCard.visibility =  View.GONE

            imgLogo.visibility = View.VISIBLE
            TitleTB.visibility = View.VISIBLE
            UsernameTB.visibility = View.VISIBLE
            PasswordTB.visibility = View.VISIBLE
            tvForgotPass.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE

            UsernameTB.text.clear()
            PasswordTB.text.clear()

            Toast.makeText(this,"Logged Out", Toast.LENGTH_LONG).show()
        }
    }
}