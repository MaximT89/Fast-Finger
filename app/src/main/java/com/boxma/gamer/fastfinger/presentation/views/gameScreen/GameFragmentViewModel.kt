package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameFragmentViewModel : ViewModel() {

    val TAG = "TAG"
    private lateinit var timer : CountDownTimer

    val isGame = MutableLiveData(false)
    val isEndLevel = MutableLiveData(false)
    val currentSecondGame = MutableLiveData(0L)
    var gameTime = 10000L

    private fun startTimer(time : Long){
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                currentSecondGame.postValue(p0 / 1000)
            }

            override fun onFinish() {
                isGame.postValue(false)
                isEndLevel.postValue(true)
                gameTime = 10000L
            }
        }.start()
    }

    fun restTimer() {
        gameTime = 10000L
        isGame.postValue(false)
    }

    fun startGame() {
        isGame.postValue(true)
        isEndLevel.postValue(false)
        startTimer(gameTime)
    }

    fun pauseGame() {
        timer.cancel()
        gameTime = currentSecondGame.value!! * 1000
        Log.d(TAG, "pauseGame: currentSecondGame ${currentSecondGame.value} and gameTime $gameTime")
    }

}