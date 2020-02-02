package com.csci448.kcooper.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val questionBank: MutableList<Question> = mutableListOf()
    private var score = 0
    var currentQuestionIndex = 0

    init {
        questionBank.add(Question(R.string.question1, false))
        questionBank.add(Question(R.string.question2, false))
        questionBank.add(Question(R.string.question3, true))
    }

    private val currentQuestion : Question
        get() = questionBank[currentQuestionIndex]

    val currentQuestionTextId: Int
        get() = currentQuestion.textResId
    val currentQuestionAnswer: Boolean
        get() = currentQuestion.isAnswerTrue
    val currentScore: Int
        get() = score

    fun moveToNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.size
    }
    fun moveToPrevQuestion() {
        currentQuestionIndex--
        if (currentQuestionIndex < 0) {
            currentQuestionIndex = questionBank.size
        }
    }
    fun isAnswerCorrect(userAnswer: Boolean) : Boolean {
        if (userAnswer == currentQuestionAnswer) {
            score++
            return true
        } else {
            return false
        }
    }
}