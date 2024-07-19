package com.wxfactory.hfxy.logic.entity

/**
 * 表示计算题、过程考核、笔记
 */
open class ScoreItem(
    val tiXin : String //题型
) {
    var index: Int? = null //题号
    var score: String? = null //分数

    //占总成绩比例，笔记固定10% ，期末考试固定50% 值在0~100
    var scoreBl: String? = null
    var kcmbF: String? = null //学生平均成绩F
    var kcmbA: String? = null //占比A%
    var kcmbH: String? = null //分数分配H

    //ui中需要的属性
    var ifChosed: Boolean = false
    fun copy(): ScoreItem {
        return ScoreItem(tiXin).also {
            it.index = index
            it.score = score
            it.scoreBl = scoreBl
            it.kcmbF = kcmbF
            it.kcmbA = kcmbA
            it.kcmbH = kcmbH
        }
    }
}