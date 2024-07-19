package com.wxfactory.hfxy.logic.caclu

import com.wxfactory.hfxy.logic.entity.Kcmb
import com.wxfactory.hfxy.logic.entity.ScoreItem
import com.wxfactory.hfxy.logic.entity.Student
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow
 




//计算平均成绩F
/**
 * kcmbH需要提前设置，期末考试的不需要
 * 期末考试的题目选中
 */
fun cacluteF(kcmb: Kcmb , stus : List<Student> ){
    
    //计算过程考核的平均成绩
    kcmb.ffH?.let {
        it.forEach {
            val thisScoreItem = it
            
            if (it.tiXin.contains("过程考核")){
                var allScore = 0.0;
                stus.forEach { 
                    it.items.find { // 找到目标过程考核
                        (thisScoreItem.index == it.index ) && it.tiXin.contains("过程考核")
                    }?.let {
                        allScore+= it.score!!.toDouble()
                    }
                }
                //过程考核平均成绩
                it.kcmbF = ( it.kcmbH!!.toDouble().div(100) * (allScore / stus.size) ).roundTo(2).toString()
            }else if (it.tiXin.contains("笔记")){
                var allScore =  0.0;
                stus.forEach {
                    it.items.find { // 找到笔记
                        (thisScoreItem.index == it.index) && it.tiXin.contains("笔记")
                    }?.let {
                        allScore+= it.score!!.toDouble()
                    }
                }
                //过程考核平均成绩
                it.kcmbF = ( it.kcmbH!!.toDouble().div(100) * (allScore / stus.size) ).roundTo(2).toString()

            }else if (it.tiXin.contains("期末考试")){
                //期末考试则需要根据选择的那些题目一起来计算平均成绩
                var allScore = 0.0;
                var scoreFull = 0.0 //满分
                stus.forEach {
                    val stu = it
                    kcmb.tiMuList?.forEach { //选中的题目
                        val chosedItem = it
                        stu.items.forEach {
                            if (chosedItem.tiXin.equals(it.tiXin) && chosedItem.index == it.index){
                                allScore+= it.score!!.toDouble()
                            }
                        }
                    }
                }

                kcmb.tiMuList?.forEach {
                    scoreFull+= it.score!!.toDouble()
                }
                
                it.kcmbH = scoreFull.toString()
                //过程考核平均成绩
                it.kcmbF = ( it.kcmbH!!.toDouble().div(100) * (allScore / stus.size) ).roundTo(2).toString()
            }
        }

    }
}




/**
 * 占比A%
 * 
 * 需要提前计算好H
 * 计算学生平均成绩
 * 占总成绩比例
 * 
 */
fun cacluteA(kcmb: Kcmb  ){

    //计算A
    kcmb.ffH?.let {
        it.forEach {
            if (it.tiXin.contains("过程考核")){
                val a = it.kcmbH!!.toDouble().div(100) * (it.scoreBl!!.toDouble().div(100))
                it.kcmbA = a.roundTo(2).toString()
            }else if (it.tiXin.contains("笔记")){
                val a = it.kcmbH!!.toDouble().div(100) * (it.scoreBl!!.toDouble().div(100))
                it.kcmbA = a.roundTo(2).toString()
            }else if (it.tiXin.contains("期末考试")){
                val a = it.kcmbF?.toDouble()?.div(it.kcmbH!!.toDouble()) 
                it.kcmbA = a?.roundTo(2).toString()
            }
        }
    }
}

/**
 * 计算各课程目标权重
 * 各课程目标权重
 */
fun cacluteOther(kcmb:Kcmb  ){
    //各课程目标权重
    kcmb.let { 
        val thisKcmb = it
        var allA  = 0.0
        it.ffH?.let{
            it.forEach {

                if (it.tiXin.contains("过程考核")){
                    allA+=it.kcmbA!!.toDouble()
                }else if (it.tiXin.contains("笔记")){
                    allA+=it.kcmbA!!.toDouble()
                }else if (it.tiXin.contains("期末考试")){
                    allA+=it.kcmbH!!.toDouble().div(200)
                }
            }
            thisKcmb
        }
        
        it.kcmbQz = allA.toDouble().roundTo(2) 
    }
    //各课程目标达成情况
    kcmb.let {
        var fenZi = 0.0
        var fenMu = 0.0
        it.ffH?.let {
            it.forEach {
                if (it.tiXin.contains("过程考核")) {
                    fenZi += it.kcmbF!!.toDouble() * it.kcmbA!!.toDouble()
                    fenMu += it.kcmbH!!.toDouble() * it.kcmbA!!.toDouble()
                } else if (it.tiXin.contains("笔记")) {
                    fenZi += it.kcmbF!!.toDouble() * it.kcmbA!!.toDouble()
                    fenMu += it.kcmbH!!.toDouble() * it.kcmbA!!.toDouble()
                } else if (it.tiXin.contains("期末考试")) {
                    fenZi += it.kcmbF!!.toDouble() / 2
                    fenMu += it.kcmbH!!.toDouble() / 2
                }
            }

        }
        it.kcmbDcqk = fenZi.div(fenMu).roundTo(2)
    }
    
}
fun cacluteMbdcd(kcmb: List<Kcmb>  ) :Double {

     var finallyScore = 0.0
     kcmb.forEach {
         finallyScore += it.kcmbQz!!.toDouble() .times(it.kcmbDcqk!!.toDouble())
     }
      return finallyScore.roundTo(2)
}

fun Double.roundTo(digits: Int): Double {
    return BigDecimal(this).setScale(digits, RoundingMode.HALF_UP).toDouble()
}