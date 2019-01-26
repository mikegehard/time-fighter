package io.github.mikegehard.timefighter

import android.os.CountDownTimer

class Game(private val length: Long,
           private val onTick: (Long) -> Unit,
           private val onEnd: () -> Unit,
           private val onReset: () -> Unit) {
    private var gameStarted = false
    private val countDownInterval = 1000L
    private var timer: CountDownTimer
    private var _score: Int = 0

    val score: Int
        get() = _score

    val timeLeft: Long
        get() = length / 1000

    init {
        timer = getTimer()
    }

    fun start() {
        if (!gameStarted) {
            timer.start()
            gameStarted = true
        }
    }

    fun incrementScore() {
        _score += 1
    }

    private fun reset() {
        timer = getTimer()
        _score = 0
        gameStarted = false
    }

    private fun getTimer(): CountDownTimer = object : CountDownTimer(length, countDownInterval) {
        override fun onFinish() {
            this@Game.onEnd()
            reset()
            this@Game.onReset()
        }

        override fun onTick(millisUntilFinished: Long) {
            this@Game.onTick(millisUntilFinished / 1000)
        }
    }
}
