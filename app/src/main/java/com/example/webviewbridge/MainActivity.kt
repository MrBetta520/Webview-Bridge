package com.example.webviewbridge

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find WebView in layout
        webView = findViewById(R.id.webView)

        // Enable JavaScript and configure the WebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        // Add the bridge interface to WebView
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidBridge")

        // Load the HTML file from assets
        webView.loadUrl("file:///android_asset/sample.html")
    }

    // This interface allows JavaScript to call Android methods
    class WebAppInterface(private val activity: MainActivity) {
        @JavascriptInterface
        fun showToast(message: String) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }

        @JavascriptInterface
        fun sendDataToAndroid(data: String) {
            Toast.makeText(activity, "Received: $data", Toast.LENGTH_LONG).show()
        }
    }
}