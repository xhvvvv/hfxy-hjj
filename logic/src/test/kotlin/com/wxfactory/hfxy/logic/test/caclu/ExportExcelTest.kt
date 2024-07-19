package com.wxfactory.hfxy.logic.test.caclu

import com.wxfactory.hfxy.logic.entity.Kcmb
import com.wxfactory.hfxy.logic.entity.ScoreItem
import com.wxfactory.hfxy.logic.excel.exportExcel
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.random.Random

class ExportExcelTest {
    
    
    @Test
    fun testCacluteF(){
        var ffH : MutableList<ScoreItem> = mutableListOf(
            ScoreItem("笔记").apply {
                index =1
                kcmbF= Random.nextDouble().toString()
                kcmbH= Random.nextDouble().toString()
                kcmbA= Random.nextDouble().toString()
                this.scoreBl = Random.nextDouble().toString()   
                this.score = Random.nextDouble().toString()
            },
            ScoreItem("考试").apply {
                index =1 
                kcmbF= Random.nextDouble().toString()
                kcmbH= Random.nextDouble().toString()
                kcmbA= Random.nextDouble().toString()
                this.scoreBl = Random.nextDouble().toString()
                this.score = Random.nextDouble().toString()
            },
            ScoreItem("过程").apply { 
                                  index=1
                kcmbF= Random.nextDouble().toString()
                kcmbH= Random.nextDouble().toString()
                kcmbA= Random.nextDouble().toString()
                this.scoreBl = Random.nextDouble().toString()
                this.score = Random.nextDouble().toString()
            },
            
            ScoreItem("过程").apply { 
                                  index=2
                kcmbF= Random.nextDouble().toString()
                kcmbH= Random.nextDouble().toString()
                kcmbA= Random.nextDouble().toString()
                this.scoreBl = Random.nextDouble().toString()
                this.score = Random.nextDouble().toString()
            },
            
            ScoreItem("过程").apply { 
                                  index=3
                kcmbF= Random.nextDouble().toString()
                kcmbH= Random.nextDouble().toString()
                kcmbA= Random.nextDouble().toString()
                this.scoreBl = Random.nextDouble().toString()
                this.score = Random.nextDouble().toString()
            },
            
        )
        
        val kcmbs = mutableListOf<Kcmb>()
        for (i in 0..5) {
            val thisOne = Kcmb()
            thisOne.kcmbQz= Random.nextDouble()
            thisOne.kcmbDcqk = Random.nextDouble()
            thisOne.tiMuList = ffH
            kcmbs.add(thisOne)
        }

        exportExcel(File("D:\\Programmer\\EPOW_ALL\\hfxy-hjj\\logic\\build\\tmp\\shit.xlsx"),kcmbs)
        
    }
}