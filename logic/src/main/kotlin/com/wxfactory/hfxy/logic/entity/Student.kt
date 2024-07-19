package com.wxfactory.hfxy.logic.entity

/**
 * 学生的成绩
 */
class Student {
    var name : String ?= null
    var index : Int ?= null
    var code : String ?= null
    var items : MutableList<ScoreItem> = mutableListOf()
}