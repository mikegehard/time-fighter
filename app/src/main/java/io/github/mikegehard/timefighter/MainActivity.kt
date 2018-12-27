package io.github.mikegehard.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView

    internal var score = 0
    internal var gameStarted = false
    internal lateinit var timer: CountDownTimer
//    internal var gameLength = 60000L
    internal var gameLength = 5000L
    internal var countDownInterval = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById(R.id.tapMeButton)
        gameScoreTextView = findViewById(R.id.gameScoreText)
        timeLeftTextView = findViewById(R.id.timeLeftText)

        tapMeButton.setOnClickListener { view ->
            startGame()
            incrementScore()
        }

        resetGame()
    }

    private fun resetGame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        val initialTimeLeft = gameLength / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTimeLeft.toString())

        timer = object : CountDownTimer(gameLength, countDownInterval) {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
            }
        }

        gameStarted = false
    }

    private fun startGame() {
        if (!gameStarted) {
            timer.start()
            gameStarted = true
        }
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over, score.toString()), Toast.LENGTH_LONG)
        println(getString(R.string.game_over, score.toString()))
        resetGame()
    }

    private fun incrementScore() {
        score += 1
        var newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore
    }
}
