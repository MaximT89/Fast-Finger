package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragmentViewModel : ViewModel() {

    val TAG = "TAG"
    private lateinit var gameTimer: CountDownTimer
    private lateinit var createItemsTimer: CountDownTimer

    val isGame = MutableLiveData(false)
    val isEndLevel = MutableLiveData(false)
    val currentSecondGame = MutableLiveData(0L)
    val itemCount = MutableLiveData(0L)
    var gameTime = 20000L


    private fun startGameTimer(time: Long) {
        gameTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(p0: Long) {
                currentSecondGame.postValue(p0 / 1000)
            }

            override fun onFinish() {
                isGame.postValue(false)
                isEndLevel.postValue(true)
            }
        }.start()
    }

    private fun startItemsTimer(time: Long, speed: Long){
        createItemsTimer = object : CountDownTimer(time, speed){
            override fun onTick(p0: Long) {
                itemCount.postValue(p0 / speed)
            }

            override fun onFinish() {
                itemCount.postValue(0)
            }
        }.start()
    }

    fun startGame() {
        isGame.postValue(true)
        isEndLevel.postValue(false)
        startGameTimer(gameTime)
        startItemsTimer(gameTime, speedCreateItems())
    }

    private fun speedCreateItems(): Long {
        // TODO: тут нужно сделать логику увеличения скорости от уровня сложности
        return 800L
    }
}