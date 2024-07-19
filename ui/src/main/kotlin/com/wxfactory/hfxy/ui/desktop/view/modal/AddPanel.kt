package com.wxfactory.hfxy.ui.desktop.view.modal

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.wxfactory.hfxy.logic.caclu.cacluteA
import com.wxfactory.hfxy.logic.caclu.cacluteF
import com.wxfactory.hfxy.logic.caclu.cacluteOther
import com.wxfactory.hfxy.logic.entity.Kcmb
import com.wxfactory.hfxy.logic.entity.ScoreItem
import com.wxfactory.hfxy.logic.entity.Student
import com.wxfactory.hfxy.ui.desktop.component.LocalAlertDialog
import com.wxfactory.hfxy.ui.desktop.datas.allKcmbs

/**
 * scoreItems，所有的选择，期末考试的每个题目，过程考核
 */
val ifChosedAready: MutableMap<String , Kcmb > = mutableMapOf<String , Kcmb >()
class AddPanel() : Screen {
    val thisKcmb: Kcmb = Kcmb()
    var tiMuList: MutableList<ScoreItem> = mutableListOf()
    lateinit var  stuList : List<Student>  

    constructor(scoreItems: List<ScoreItem>, stus : List<Student>) : this() {
        scoreItems.filter {
            it.tiXin.contains("过程考核")  
        }.forEach {
            thisKcmb.ffH.add(it.copy())
        }
        
        scoreItems.filter {
             it.tiXin.contains("笔记")
        }.forEach {
            thisKcmb.ffH.add(it.copy().apply { 
                this.scoreBl = "10" //笔记固定为10%
            })
        }

        scoreItems.filter {
            !it.tiXin.contains("过程考核") && !it.tiXin.contains("笔记")
        }.forEach {
            tiMuList.add(it.copy())
        }

        thisKcmb.ffH.add(ScoreItem("期末考试").apply {
            this.index = 1
            this.scoreBl = "50" //期末考试固定为50%
        })
        stuList=stus;
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        Scaffold(
            modifier = Modifier.fillMaxHeight(0.6f),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End, // 从末尾开始排列
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val localAlertDialog = LocalAlertDialog.current
                    FilledTonalButton(
                        onClick = {
                            //校验分数分配是否超出范围
                            try {
                                //计算
                                cacluteF( thisKcmb , stuList )
                                cacluteA( thisKcmb   )
                                cacluteOther( thisKcmb )
                                allKcmbs.add(thisKcmb)
                                thisKcmb.tiMuList.forEach {
                                    ifChosedAready["${it.tiXin}${it.index}"] = thisKcmb
                                }
                                bottomSheetNavigator.hide()
                            }catch (e:Exception){
                                localAlertDialog.alert("填写数据异常，请检查！")
                            }
                        }
                    ) {
                        Text("添加")
                    }
                }
            }
        ) {
            val scrollState = rememberScrollState()
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier.fillMaxSize().padding(it).verticalScroll(scrollState)

            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("添加课程目标")
                }
                FlowRow(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    thisKcmb.ffH.filter {
                        it.tiXin.contains("过程考核") || it.tiXin.contains("笔记")
                    }.forEach {
                        val thisOne = it
                        var allH = remember {
                            var allH = 0.0
                            //计算剩下的范围
                            allKcmbs.forEach {
                                it.ffH.forEach {
                                    if (it.tiXin == thisOne.tiXin && it.index == thisOne.index ) {
                                        allH += it.kcmbH!!.toDouble()
                                    }
                                }
                            }
                            allH
                        }
                        var textState by remember { mutableStateOf(thisOne.kcmbH ?: "") }
                        TextField(
                            value = textState,
                            onValueChange = {
                                textState = it
                                thisOne.kcmbH = it
                            },
                            label = { Text("分数分配：${thisOne.tiXin}${thisOne.index}")},
                            placeholder = { Text("请输入分数分配：0~${100-allH}") },
                        )
                    }
                }
                tiMuList.filter {
                    !it.tiXin.contains("过程考核") && !it.tiXin.contains("笔记")
                }.groupBy {
                    it.tiXin
                }.forEach {
                    FlowRow(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text("${it.key}：")
                       
                        it.value.forEach {
                            val thisOne = it
                            if ( ifChosedAready.containsKey("${it.tiXin}${it.index}") 
                                && ifChosedAready["${it.tiXin}${it.index}"] !=thisKcmb
                            ) {
                                Text("${it.index} ")
                                Checkbox(
                                    enabled = false,
                                    checked = false,
                                    onCheckedChange = {

                                    }
                                )
                            } else {
                                val checkedState = remember { mutableStateOf(false) }
                                Text("${it.index} ")
                                Checkbox(
                                    checked = checkedState.value,
                                    onCheckedChange = {
                                        checkedState.value = it
                                        if (it) {
                                            thisKcmb.tiMuList.add(thisOne)
                                            thisOne.ifChosed = true
                                        } else {
                                            thisKcmb.tiMuList.remove(thisOne)
                                            thisOne.ifChosed = false
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}