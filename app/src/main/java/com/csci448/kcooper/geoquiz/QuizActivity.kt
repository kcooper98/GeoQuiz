package com.csci448.kcooper.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_quiz.*

private const val LOG_TAG = "448.QuizActivity"
private const val KEY_INDEX = "index"
private const val SCORE_INDEX = "scoreIndex"

class QuizActivity : AppCompatActivity() {
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var scoreTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        setContentView(R.layout.activity_quiz)

        val factory = QuizViewModelFactory()
        quizViewModel = ViewModelProvider(this@QuizActivity, factory)
            .get(QuizViewModel::class.java)

        val storedIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentQuestionIndex = storedIndex

        val storedScore = savedInstanceState?.getInt(SCORE_INDEX, 0) ?: 0
        quizViewModel.score = storedScore

        scoreTextView = findViewById(R.id.score_text_view)
        val trueButton: Button = findViewById(R.id.true_button)
        val falseButton: Button = findViewById(R.id.false_button)
        val nextButton: Button = findViewById(R.id.next_button)
        val prevButton: Button = findViewById(R.id.previous_button)
        val cheatButton: Button = findViewById(R.id.cheat_start_button)

        updateQuestion()

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }
        nextButton.setOnClickListener { moveToQuestion(1) }
        prevButton.setOnClickListener { moveToQuestion(-1) }
        cheatButton.setOnClickListener{ launchCheat() }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(LOG_TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentQuestionIndex)
        savedInstanceState.putInt(SCORE_INDEX, quizViewModel.currentScore)
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy() called")
    }

    private fun moveToQuestion(direction: Int) {
        if (direction > 0) {
            quizViewModel.moveToNextQuestion()
        } else {
            quizViewModel.moveToPrevQuestion()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        setCurrentScoreText()
        question_text_view.text = resources.getString(quizViewModel.currentQuestionTextId)
    }

    private fun setCurrentScoreText() {
        scoreTextView.text = quizViewModel.currentScore.toString()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (quizViewModel.isAnswerCorrect(userAnswer)) {
            setCurrentScoreText()
            Toast.makeText(
                baseContext,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                baseContext,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun launchCheat() {
        val isAnswerTrue = quizViewModel.currentQuestionAnswer
        val intent = CheatActivity.createIntent(this@QuizActivity, isAnswerTrue)
        startActivity(intent)
    }
}
