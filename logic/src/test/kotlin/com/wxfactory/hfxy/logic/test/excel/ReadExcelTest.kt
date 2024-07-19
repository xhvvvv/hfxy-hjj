package com.wxfactory.hfxy.logic.test.excel

import com.wxfactory.hfxy.logic.excel.readExcel
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Test
class ReadExcelTest {
    @Test
    fun shit() {
        val  doc = XSSFWorkbook("D:\\Programmer\\EPOW_ALL\\hfxy-hjj\\doc\\副本录入成绩模板（标黄区域勿修改）.xlsx")

        val cons = readExcel(doc)
        for (con in cons) {
            println(con)
        }
    }
}
