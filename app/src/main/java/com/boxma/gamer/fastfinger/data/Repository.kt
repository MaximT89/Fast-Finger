package com.boxma.gamer.fastfinger.data

import android.content.Context
import com.boxma.gamer.fastfinger.data.storage.StoragePrefs
import com.boxma.gamer.fastfinger.data.storage.StoragePrefs.score
import javax.inject.Inject

class Repository @Inject constructor(context: Context) {

    private val prefs = StoragePrefs.defaultPref(context)

    fun updateScore(value: Int) {
        prefs.score = prefs.score + value
    }

    fun getScore() = prefs.score

    fun removeScore() {
        prefs.score = 0
    }
}
