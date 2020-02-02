package com.csci448.kcooper.geoquiz

data class Question( val _id: Int, val _answer : Boolean) {
    val textResId : Int = _id
    val isAnswerTrue : Boolean = _answer
}