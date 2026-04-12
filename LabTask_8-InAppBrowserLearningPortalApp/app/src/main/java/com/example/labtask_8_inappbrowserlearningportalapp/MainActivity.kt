package com.example.labtask_8_inappbrowserlearningportalapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.KeyEvent
import android.widget.EditText
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    lateinit var etUrl: EditText
    lateinit var btnBack : ImageButton
    lateinit var btnForward: ImageButton
    lateinit var btnRefresh : ImageButton
    lateinit var btnHome : ImageButton
    lateinit var btnGo : Button

    val homeUrl = "https://www.google.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webView = findViewById<WebView>(R.id.webView)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        etUrl = findViewById(R.id.etUrl)
        btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnForward = findViewById<ImageButton>(R.id.btnForward)
        btnRefresh = findViewById<ImageButton>(R.id.btnRefresh)
        btnHome = findViewById<ImageButton>(R.id.btnHome)
        btnGo = findViewById<Button>(R.id.btnGo)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                progressBar.visibility = View.VISIBLE
                etUrl.setText(url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                webView.loadUrl("file:///android_asset/offline.html")
            }
        }

        webView.webChromeClient = object : android.webkit.WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }

        webView.loadUrl(homeUrl)

        btnBack.setOnClickListener {
            if(webView.canGoBack()){
                webView.goBack()
            }else{
                Toast.makeText(this,"No history", Toast.LENGTH_SHORT).show()
            }
        }

        btnForward.setOnClickListener {
            if (webView.canGoForward()){
                webView.goForward()
            }
        }

        btnRefresh.setOnClickListener {
            webView.reload()
        }

        btnHome.setOnClickListener {
            webView.loadUrl(homeUrl)
        }

        btnGo.setOnClickListener {
            loadUrlFromInput()
        }

        etUrl.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                loadUrlFromInput()
                true
            }else{
                false
            }
        }

        setupShortcuts()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (webView.canGoBack()){
                    webView.goBack()
                }else{
                    finish()
                }
            }
        })
    }

    private fun loadUrlFromInput() {
        var url = etUrl.text.toString().trim()

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://$url"
        }

        if (isConnected()) {
            webView.loadUrl(url)
        } else {
            webView.loadUrl("file:///android_asset/offline.html")
        }
    }

    private fun isConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun setupShortcuts() {

        val google = findViewById<Button>(R.id.btnGoogle)
        val youtube = findViewById<Button>(R.id.btnYoutube)
        val wiki = findViewById<Button>(R.id.btnWiki)
        val uni = findViewById<Button>(R.id.btnAIUB)

        google.setOnClickListener { webView.loadUrl("https://www.google.com") }
        youtube.setOnClickListener { webView.loadUrl("https://www.youtube.com") }
        wiki.setOnClickListener { webView.loadUrl("https://www.wikipedia.org") }
        uni.setOnClickListener { webView.loadUrl("https://www.aiub.edu") }
    }
}
