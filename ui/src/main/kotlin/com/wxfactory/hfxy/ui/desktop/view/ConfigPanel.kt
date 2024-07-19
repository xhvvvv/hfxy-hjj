package com.wxfactory.hfxy.ui.desktop.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

/**
 * 面板总成
 */
@Stable
class ConfigPanel(val items:List<Tab>) : Screen {
    @Composable
    override fun Content() {
        TabNavigator(
            items.first(),
        ) {
            Row {
                NavigationRail(
                    modifier = Modifier.fillMaxHeight().alpha(0.95F),
                    containerColor = MaterialTheme.colorScheme.surface,
                    header = {
                        /*Icon(
                            modifier = Modifier.size(42.dp),
                            imageVector = Icons.Default.AccountBox,
                            // painter = painterResource("n_logo.png"),
                            contentDescription = "Logo",
                        )*/
                    },
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    items.forEachIndexed { index, item ->
                        val isSelected = (it.current == item)
                        NavigationRailItem(
                            modifier = Modifier.padding(vertical = 12.dp),
                            alwaysShowLabel = true,
                            icon = {
                                item.options.icon?.let {
                                    Icon(
                                        painter = if (isSelected) {
                                            it
                                        } else {
                                            it
                                        },
                                        contentDescription = item.options.title,
                                    )
                                }
                            },
                            label = { Text(text = item.options.title) },
                            selected = isSelected,
                            onClick = { it.current = item }
                        )
                    }
                }
                CurrentTab()
//                CurrentScreen() //当前应该展示的屏幕
            }
        }
    }
}

