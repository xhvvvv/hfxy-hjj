package com.wxfactory.hfxy.ui.desktop.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.wxfactory.hfxy.ui.desktop.component.AlertDialogDefaults
import com.wxfactory.hfxy.ui.desktop.component.theme.MyMaterialTheme
import com.wxfactory.hfxy.ui.desktop.view.tabs.AboutTab
import com.wxfactory.hfxy.ui.desktop.view.tabs.MainTab

@Composable
fun MainView() {
    MyMaterialTheme( )  {
        AlertDialogDefaults{
            //这里等待数据加载完成后再初始化和
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                //主界面，这里只有一个界面
                Navigator(
                    screen =  
                        ConfigPanel(items = listOf(
                            MainTab(), 
                            AboutTab()
                        ))
                )
            }
        }
        
    }
}
