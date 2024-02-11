package com.example.vkm.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import com.example.vkm.SecurityData

class LogInActivity : ComponentActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        var url = "https://oauth.vk.com/authorize?client_id=2685278&redirect_uri=https%3A%2F%2Foauth.vk.com%2Fblank.html&response_type=token&scope=1040183263"

        webView = WebView(this)
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        setContentView(webView)

        webView.webViewClient = object : WebViewClient() {
            @SuppressLint("NewApi")
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                url = request.url.toString()
                if (url.contains("https://oauth.vk.com/blank.html#access_token")){
                    handleAuthUrl(url)
                } else {

                    webView.loadUrl(url)
                }
                return true
            }
        }
    }
    private fun handleAuthUrl(url: String) {
        val securityData = SecurityData(this)
        securityData.putData("token", extractValueFromUrl(url, "access_token"))
        securityData.putData("user_id", extractValueFromUrl(url, "user_id"))
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun extractValueFromUrl(url: String, parameter: String): String? {
        val start = url.indexOf("$parameter=")
        if (start == -1) {
            return null
        }

        val end = url.indexOf('&', start)
        val valueEnd = if (end == -1) url.length else end

        return url.substring(start + parameter.length + 1, valueEnd)
    }
}

