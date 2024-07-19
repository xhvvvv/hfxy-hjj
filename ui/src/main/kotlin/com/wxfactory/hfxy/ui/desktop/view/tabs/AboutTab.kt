package com.wxfactory.hfxy.ui.desktop.view.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.outlined.Engineering
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.wxfactory.hfxy.logic.excel.exportExcel
import com.wxfactory.hfxy.ui.desktop.datas.allKcmbs
import org.apache.commons.io.FileUtils
import java.awt.FileDialog
import java.io.File


class AboutTab() : Tab{
    override val options: TabOptions
        @Composable
        get() {
            val title = "作者"
            val imageVector = rememberVectorPainter( Icons.Outlined.Engineering)
            return TabOptions(
                index = 1u,
                title = "作者",
                icon = imageVector,
            )
        }
    @Composable
    override fun Content() {
        aboutPanel()
    }

}

@Composable
  fun aboutPanel( )  {
    Scaffold(){
        Card(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.scale(0.6f) ,
                    painter = painterResource("image/wxfactory.jpg"),
                    contentDescription = "伟行工厂"
                )
                Text("伟行工厂制")
            }
            Column(
                modifier = Modifier
//                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .padding(start = 50.dp, top = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "版本信息",
                    style =  MaterialTheme.typography.titleLarge
                )
                Divider(thickness=5.dp)

                Row {
                    Icon(
                        modifier = Modifier.scale(0.3f),
                        imageVector = Icons.Filled.FiberManualRecord,
                        contentDescription = "版本",
                    )
                    Text(
                        "0.0.1",
                    )
                }

                Row {
                    Icon(
                        modifier = Modifier.scale(0.3f),
                        imageVector = Icons.Filled.FiberManualRecord,
                        contentDescription = "版本说明",
                    )
                    Text(
                        "合肥大学分数计算程序",
                    )
                }

               

            }

            Column(
                modifier = Modifier
//                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .padding(start = 50.dp, top = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "支持信息",
                    style =  MaterialTheme.typography.titleLarge
                )
                Divider(thickness=5.dp)
                Row {
                    val uriHandler = LocalUriHandler.current
                    Icon(
                        modifier = Modifier.scale(0.3f),
                        imageVector = Icons.Filled.FiberManualRecord,
                        contentDescription = "版本说明",
                    )
                    ClickableText(
                        text = buildAnnotatedString {
                            val text ="开源地址 https://github.com/xhvvvv/hfxy-hjj.git"
                            append(text)
                            addStyle(SpanStyle(textDecoration = TextDecoration.Underline), 0, text.length)
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        onClick = {
                            uriHandler.openUri("https://github.com/xhvvvv/hfxy-hjj.git")
                        }
                    )
                }
                Row {
                    Icon(
                        modifier = Modifier.scale(0.3f),
                        imageVector = Icons.Filled.FiberManualRecord,
                        contentDescription = "联系我",
                    )
                    Text(
                        "联系我 wj1939146725@gmail.com",
                    )
                }
            }


            Column(
                modifier = Modifier
//                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .padding(start = 50.dp, top = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            try {
                                val fileDialog = FileDialog(ComposeWindow(),"选择下载的位置", FileDialog.LOAD) 
                                fileDialog.file = "template"
                                fileDialog.isVisible = true
                                val directory = fileDialog.directory
                                directory?.let {
                                    val name = (fileDialog.file?:"template" )+".xlsx"
                                    
                                    
                                    object {}.javaClass.classLoader.getResourceAsStream("test.xlsx").use { 
                                        FileUtils.copyInputStreamToFile(it,File("$directory/$name"))
                                    }
                                }
                            }catch (e:Exception){
                                e.printStackTrace()
                            }
                        },
                        text = { Text("模板下载") },
                        icon = { Icon(Icons.Filled.Download, contentDescription = "模板下载") },
                    )
                }
            }
        }
    }
}
