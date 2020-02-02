package com.csci448.kcooper.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_quiz.*

private const val LOG_TAG = "448.QuizActivity"
private const val TAG = "QuizActivity"
private const val KEY_INDEX = "index"

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

        scoreTextView = findViewById(R.id.score_text_view)
        val trueButton: Button = findViewById(R.id.true_button)
        val falseButton: Button = findViewById(R.id.false_button)
        val nextButton: Button = findViewById(R.id.next_button)
        val prevButton: Button = findViewById(R.id.previous_button)

        updateQuestion();

        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }
        nextButton.setOnClickListener { moveToQuestion(1) }
        prevButton.setOnClickListener { moveToQuestion(-1) }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentQuestionIndex)
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
}
