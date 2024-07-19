package com.wxfactory.hfxy.logic.excel

import com.wxfactory.hfxy.logic.caclu.cacluteMbdcd
import com.wxfactory.hfxy.logic.entity.Kcmb
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

fun exportExcel(file: File, kcmbs : List<Kcmb>){
    val doc: XSSFWorkbook = XSSFWorkbook()
    val sheet = doc.createSheet()
    val row0 = sheet.createRow(0)
    row0.let {
        
        it.createCell(0) .setCellValue("考核方式")
        it.createCell(1)

        for (i in kcmbs.indices) {
            it.createCell(i+2).let {
                it.setCellValue("课程目标${i+1}")
            }
        }
       
        it.createCell(kcmbs.size + 2).let {
            it.setCellValue("总分")
        }
        it.createCell(kcmbs.size + 3).let {
            it.setCellValue("占总成绩比例")
        }
    }
    val allItem = kcmbs[0].ffH
    var nowRow  = 1
    allItem.forEach {
        val topIt = it
        val row1 = sheet.createRow(nowRow++)
        val row2 = sheet.createRow(nowRow++)
        val row3 = sheet.createRow(nowRow++)
        row1.createCell(0).setCellValue("${it.tiXin}${it.index}")
        row1.createCell(1).setCellValue("分数分配H")
        for (i in kcmbs.indices) {
            val thisGay = kcmbs[i].ffH.find {
                topIt.tiXin == it.tiXin && topIt.index == it.index
            }
            row1.createCell(i+2).setCellValue(thisGay?.kcmbH)
        }
        row1.createCell(kcmbs.size + 2).setCellValue("100")
        row1.createCell(kcmbs.size + 3).setCellValue(it.scoreBl)

        row2.createCell(0) 
        row2.createCell(1).setCellValue("学生平均成绩F")
        for (i in kcmbs.indices) {
            val thisGay = kcmbs[i].ffH.find {
                topIt.tiXin == it.tiXin && topIt.index == it.index
            }
            row2.createCell(i+2).setCellValue(thisGay?.kcmbF)
        }
        row2.createCell(kcmbs.size + 2) 
        row2.createCell(kcmbs.size + 3) 


        row3.createCell(0)
        row3.createCell(1).setCellValue("占比A%")
        for (i in kcmbs.indices) {
            val thisGay = kcmbs[i].ffH.find {
                topIt.tiXin == it.tiXin && topIt.index == it.index
            }
            row3.createCell(i+2).setCellValue(thisGay?.kcmbA)
        }
        row3.createCell(kcmbs.size + 2) 
        row3.createCell(kcmbs.size + 3) 

        
    }
    
    //各课程目标权重
    sheet.createRow(nowRow++).let {

        it.createCell(0).setCellValue("各课程目标权重")
        it.createCell(1)
        var sum = 0.0       
        for (i in kcmbs.indices) {
            it.createCell(i+2).let {
                it.setCellValue(kcmbs[i].kcmbQz.toString())
            }
            sum += kcmbs[i].kcmbQz!!.toDouble()
        }

        it.createCell(kcmbs.size + 2).let {
            it.setCellValue("")
        }
        it.createCell(kcmbs.size + 3).let {
            it.setCellValue(sum.toString())
        }
    }
    
    
    //各课程目标权重
    sheet.createRow(nowRow++).let {

        it.createCell(0).setCellValue("各课程目标达成情况")
        it.createCell(1)
        for (i in kcmbs.indices) {
            it.createCell(i+2).let {
                it.setCellValue(kcmbs[i].kcmbDcqk.toString())
            }
        }

        it.createCell(kcmbs.size + 2).let {
            it.setCellValue("")
        }
        it.createCell(kcmbs.size + 3).let {
            it.setCellValue("")
        }
    }
    
    //各课程目标权重
    sheet.createRow(nowRow++).let {
        it.createCell(0).setCellValue("该课程的课程目标达成度")
        it.createCell(1)
        it.createCell(2).setCellValue(cacluteMbdcd(kcmbs))
    }
    doc.write(FileOutputStream(file))
    doc.close()
}