package com.wxfactory.hfxy.ui.desktop.view.tabs
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.wxfactory.hfxy.logic.caclu.cacluteMbdcd
import com.wxfactory.hfxy.logic.entity.Student
import com.wxfactory.hfxy.logic.excel.exportExcel
import com.wxfactory.hfxy.logic.excel.readExcel
import com.wxfactory.hfxy.ui.desktop.component.LocalAlertDialog
import com.wxfactory.hfxy.ui.desktop.datas.allKcmbs
import com.wxfactory.hfxy.ui.desktop.view.modal.AddPanel
import com.wxfactory.hfxy.ui.desktop.view.modal.ifChosedAready
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.awt.FileDialog
import java.io.File
import java.io.FilenameFilter
import java.io.PrintStream

class MainTab() : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val imageVector = rememberVectorPainter(Icons.Outlined.FlagCircle)
            return TabOptions(
                index = 0u,
                title = "主",
                icon = imageVector,
            )
        }
    @Composable
    override fun Content() {

        SettingsScreen()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen() {
    val fontStyle =  MaterialTheme.typography.labelLarge.copy(
        fontWeight = FontWeight.SemiBold,
    )
    
    BottomSheetNavigator(
        sheetShape = MaterialTheme.shapes.medium,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        val fileChose  = remember  { mutableStateOf<File?>(null) } 
        var allStu  = remember<List<Student>?> { null }
        val localAlertDialog = LocalAlertDialog.current
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Row(
                        ) {
                            IconButton(
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),
                                onClick = {
                                    try {
                                        val fileDialog = FileDialog(ComposeWindow(),"选择导出的位置", FileDialog.LOAD).apply {
                                            this.filenameFilter = FilenameFilter { dir, name -> 
                                                name.endsWith(".xlsx")
                                            }
                                            this.file= "成绩表.xlsx"
                                        }
                                        fileDialog.isVisible = true
                                        val directory = fileDialog.directory
                                        val file = fileDialog.file
                                        file?.let {
                                            exportExcel(File("${directory}${file}"), allKcmbs)
                                        }
                                    }catch (e:Exception){
                                        e.printStackTrace()
                                        localAlertDialog.alert("导出失败！")
                                    }
                                }
                            ) {
                                Icon(
                                    painter = rememberVectorPainter(image = Icons.Outlined.FileUpload),
                                    contentDescription = "导出计算结果",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                            IconButton(
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),
                                onClick = {
                                    allKcmbs.clear()
                                    try {
                                        val fileDialog = FileDialog(ComposeWindow(),"选择分数文件")
                                        fileDialog.isVisible = true
                                        val directory = fileDialog.directory
                                        val file = fileDialog.file
                                        if (directory != null && file != null) {
                                            val xsffBook = XSSFWorkbook("$directory$file")
                                            allStu = readExcel(xsffBook)
                                            fileChose.value = File("$directory$file")
                                            val first = allStu!![0]

                                            var kljasjdfkl = ""
                                            first.items.groupBy {
                                                it.tiXin
                                            }.forEach { t, u ->
                                                kljasjdfkl += "$t : ${u.size}题、"
                                            }
                                            localAlertDialog.alert(
                                                """
                                                |已选中文件$file
                                                |读取内容如下：
                                                |    $kljasjdfkl
                                                |    学生${allStu!!.size - 1}人
                                            """.trimMargin()
                                            )
                                        }
                                    }catch (e:Throwable){
                                        e.printStackTrace(PrintStream(File("D:\\aaaa\\wron") ))
                                        allKcmbs.clear()
                                        localAlertDialog.alert("处理文件失败，请检查文件内容！")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Folder,
                                    contentDescription = "选择分数表格",
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            
                            Text(
                                modifier = Modifier.padding(top = 10.dp) ,
                                text = if(fileChose.value!=null) "已选择${fileChose.value!!.name}" else "未选择学生分数文件",
                                textAlign= TextAlign.Center,)
                            
                            Spacer(modifier = Modifier.width(10.dp))
                            
                            Text(
                                modifier = Modifier.padding(top = 10.dp) ,
                                text = "已计算目标达成情况：${cacluteMbdcd(allKcmbs)}",
                                textAlign= TextAlign.Center,)
                        }
                    },
                    floatingActionButton = {
                        val bottomSheetNavigator = LocalBottomSheetNavigator.current
                        FloatingActionButton(
                            onClick = {
                                if (fileChose.value!=null && fileChose.value!!.exists()) {
                                    bottomSheetNavigator.show(
                                        AddPanel(allStu!![0].items , allStu!!.toMutableList().apply { 
                                            this.removeAt(0)
                                        })
                                    )
                                }else{
                                    localAlertDialog.alert("错误","未选择学生分数文件")
                                }
                            },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Add, "添加配置")
                        }
                    }
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                allKcmbs.forEachIndexed { index, kcmb ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(10.dp),
                    ) {
                       
                        Row(
                            modifier = Modifier.fillMaxSize().padding(5.dp),
                            horizontalArrangement =  Arrangement.SpaceBetween
                        ){
                            Column(
                                modifier = Modifier.fillMaxHeight().width(20.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("课程目标${index+1}")
                             
                            }
                            
                            Spacer(modifier = Modifier.fillMaxHeight().width(3.dp).background(color = MaterialTheme.colorScheme.onPrimary))
                            kcmb.ffH.forEach { 
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${it.tiXin}${it.index}")
                                    Text("总分：100",
                                        style = fontStyle)
                                    Text("占总成绩比例：${it.scoreBl}",
                                        style = fontStyle)
                                    Text(
                                        "分数分配：${it.kcmbH}" ,
                                        style = fontStyle)
                                    Text("占比：  ${it.kcmbA}",
                                        style = fontStyle)
                                    Text("平均成绩：${it.kcmbF} ",
                                        style = fontStyle)
                                }
                                Spacer(modifier = Modifier.fillMaxHeight().width(3.dp).background(color = MaterialTheme.colorScheme.onPrimary))
                            }

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("目标权重：${kcmb.kcmbQz}",
                                    style = fontStyle)
                                Text("目标达成情况：${kcmb.kcmbDcqk}",
                                    style = fontStyle)
                            }
                            Spacer(modifier = Modifier.fillMaxHeight().width(3.dp).background(color = MaterialTheme.colorScheme.onPrimary))

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                kcmb.tiMuList.groupBy { 
                                    it.tiXin
                                }.forEach {
                                    Text("${it.key}：${it.value.map { it.index }.joinToString()}",
                                        style = fontStyle)
                                }
                                IconButton(
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                    ),
                                    onClick = {
                                        kcmb.tiMuList.forEach {
                                            ifChosedAready.remove("${it.tiXin}${it.index}")
                                        }
                                        allKcmbs.remove(kcmb) 
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier ,
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "删除",
                                    )
                                }
                                
                            }
                        }
                    }
                }
            }
        }
    }
}