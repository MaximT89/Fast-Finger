package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxma.gamer.fastfinger.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val TAG = "TAG"
    private var gameTimer: CountDownTimer? = null
    private var createItemsTimer: CountDownTimer? = null

    val isGame = MutableLiveData(false)
    val isEndLevel = MutableLiveData(false)
    val currentSecondGame = MutableLiveData(0L)
    val itemCount = MutableLiveData(0L)
    val lifes = MutableLiveData(repository.getMaxLife())
    var gameTime = 20000L
//    var gameTime = 4000L

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

    private fun startItemsTimer(time: Long, speed: Long) {
        createItemsTimer = object : CountDownTimer(time, speed) {
            override fun onTick(p0: Long) {
                itemCount.postValue(p0 / speed)
            }

            override fun onFinish() {
                itemCount.postValue(0)
            }
        }.start()
    }

    fun minusLife() {
        if(repository.getCurrentLife() > 0){
            lifes.postValue(lifes.value?.minus(1))
            repository.minusCurrentLife(1)
        }
    }

    fun plusLife() {
        if(repository.getCurrentLife() <= repository.getMaxLife()){
            lifes.postValue(lifes.value?.plus(1))
            repository.plusCurrentLife(1)
        }
    }

    fun startGame() {
        lifes.postValue(repository.getMaxLife())
        isGame.postValue(true)
        isEndLevel.postValue(false)
        startGameTimer(gameTime)
        startItemsTimer(gameTime, speedCreateItems())
    }

    fun stopGame() {
        isGame.postValue(false)
        gameTimer?.cancel()
        createItemsTimer?.cancel()
    }

    private fun speedCreateItems(): Long {
        // TODO: тут нужно сделать логику увеличения скорости от уровня сложности
        return 800L
    }

}