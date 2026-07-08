package com.exquisite.a_mobile_kmm.core.screen_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import io.ktor.http.Url

@Composable
fun WebViewUrlScreen(url:String, modifier: Modifier = Modifier,goBackWithTransRef:(String) ->Unit) {
    Box(modifier = modifier.fillMaxSize()){

        val state = rememberWebViewState(url = url)

        LaunchedEffect(state.lastLoadedUrl) {
            state.lastLoadedUrl?.let { url ->
                println("URL changed to: $url")
                if(url.contains("https://www.amobilegroup.com",ignoreCase = true)){
                    println("_i_enter_here")

                    goBackWithTransRef( Url(url).parameters["transaction_id"]?:"")
                }

            }
        }

        WebView(
            state = state,
            modifier = Modifier.fillMaxSize()
        )
    }
}