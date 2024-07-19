package com.wxfactory.hfxy.logic.excel

import com.wxfactory.hfxy.logic.entity.ScoreItem
import com.wxfactory.hfxy.logic.entity.Student
import com.wxfactory.hfxy.logic.excel.entity.ExamUnitRow
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.xssf.usermodel.XSSFWorkbook


//读取表格的数据
fun readExcel(doc:XSSFWorkbook):List<Student> {
    val stuList:MutableList<Student> = mutableListOf ()
    val dataFormatter = DataFormatter()
    val sheet = doc.getSheetAt(0);
    val firstRow = sheet.getRow(0)
    val allMerged  =sheet.mergedRegions;
    //找到第一行合并的那些单元格
    val titles :MutableList<ExamUnitRow> = mutableListOf() 
    allMerged.forEach{
        if (it.firstRow == 0){
            val thisRow = sheet.getRow(it.firstRow)
            val thisCell = thisRow.getCell(it.firstColumn)
            val name = dataFormatter.formatCellValue(thisCell)   //题目名称
            val examUnitRow : ExamUnitRow = ExamUnitRow(name)
            examUnitRow.startCell = it.firstColumn
            examUnitRow.endCell = it.lastColumn
            titles.add(examUnitRow)
        }
    }
    //找第一行没有合并的那些单元
    firstRow.cellIterator().forEach {
        val name = dataFormatter.formatCellValue(it)    //题目名称
        for (title in titles) {
            if(title.startCell <= it.columnIndex  && it.columnIndex <= title.endCell){
                return@forEach
            }
            
            if (name.isBlank() ||  title.tiXin.equals(name)){
                return@forEach
            } 
        }
        
        
        val examUnitRow : ExamUnitRow = ExamUnitRow(name)
        examUnitRow.startCell = it.columnIndex
        examUnitRow.endCell = it.columnIndex
        titles.add(examUnitRow)
    }
    
    //第三行，找到满分数
    val viturlStu : Student = Student()
    val threeRow = sheet.getRow(2)
    
    titles.forEach {
        var j = 1
        for (i in  it.startCell ..  it.endCell) {
            val thisOne : ScoreItem = ScoreItem(it.tiXin)
            val score = threeRow.getCell(i)
            thisOne.index = j++
            thisOne.score = score.toString()
            thisOne.scoreBl = score.toString()
            viturlStu.items.add(thisOne)
        }
    }
    stuList.add(viturlStu)
    //从第四行开始，找到所有学生的分数
    val lastRowIndex = sheet.lastRowNum
    for (i in (3.. lastRowIndex)) {
        val thisStu : Student = Student()
        val thisRow = sheet.getRow(i);
         
        thisRow?.getCell(2)?.let {
            dataFormatter.formatCellValue(it)
        }?.isNotBlank()?.let {
            if (it){
                thisRow.getCell(2)?.let {
                    dataFormatter.formatCellValue(it)
                }?.let {
                    thisStu.name = it
                    thisRow.getCell(0)?.let { 
                        dataFormatter.formatCellValue(it)
                    }?.let { thisStu.index = it.toInt() }
                    thisRow.getCell(1)?.rawValue?.let { thisStu.code = it }
                    //找到这个学生所有的成绩
                    titles.forEach {
                        var j = 1
                        for (i in  it.startCell ..  it.endCell) {
                            val thisOne : ScoreItem = ScoreItem(it.tiXin)
                            val score = thisRow.getCell(i)
                            thisOne.index = j++
                            thisOne.score = score.toString()
                            thisStu.items.add(thisOne)
                        }
                    }
                    stuList.add(thisStu)
                }
            }
        }
    }  
    
    return  stuList;
}