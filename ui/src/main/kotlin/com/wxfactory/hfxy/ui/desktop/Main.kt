package com.wxfactory.hfxy.ui.desktop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.wxfactory.hfxy.ui.desktop.view.MainView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = application {
    
 
        val icon = painterResource("image/hfxy2.svg")
        val state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = 1100.dp,
            height = 700.dp,
        )
        Window(
            onCloseRequest = { exitApplication()  },
            title = "合肥大学计分程序",
            state = state,
            icon = icon
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
               
                MainView()
            }
        }
   
    
}