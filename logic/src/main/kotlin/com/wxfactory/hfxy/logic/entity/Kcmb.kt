package com.wxfactory.hfxy.logic.entity

/**
 * 课程目标的实体类
 */
class Kcmb() {

    //目标权重
    var kcmbQz : Double ?= null
    //目标达成情况
    var kcmbDcqk : Double ?= null
    
    //这里存储的仅是期末考试中的选中的题目
    var tiMuList : MutableList<ScoreItem>  = mutableListOf()
    
    //期末考试，过程考，笔记的每项，对应结果表中的纵座标
    var ffH : MutableList<ScoreItem>  = mutableListOf()
}