package io.github.mikegehard.timefighter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById(R.id.tapMeButton)
        gameScoreTextView = findViewById(R.id.gameScoreText)
        timeLeftTextView = findViewById(R.id.timeLeftText)

        tapMeButton.setOnClickListener {
            game.start()
            incrementScore()
        }

        val onTick: (Long) -> Unit = {
            timeLeftTextView.text = getString(R.string.time_left, it.toString())
        }

        val onEnd: () -> Unit = {
            Toast.makeText(this, getString(R.string.game_over,  game.score.toString()), Toast.LENGTH_LONG)
        }

        val onReset: () -> Unit = {
            updateDisplay()
        }

        game = Game(5000L, onTick, onEnd, onReset)

        updateDisplay()
    }

    private fun updateDisplay() {
        gameScoreTextView.text = getString(R.string.your_score, game.score.toString())
        timeLeftTextView.text = getString(R.string.time_left, game.timeLeft.toString())
    }

    private fun incrementScore() {
        game.incrementScore()
        gameScoreTextView.text = getString(R.string.your_score, game.score.toString())
    }
}
