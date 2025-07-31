package com.example.universityapp.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    val state = rememberWebViewState(url = url)

    WebView(
        state = state,
        modifier = Modifier,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        },
        client = AccompanistWebViewClient()
    )
}