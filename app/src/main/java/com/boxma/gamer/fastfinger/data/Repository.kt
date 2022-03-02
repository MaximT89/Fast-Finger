package com.boxma.gamer.fastfinger.data

import android.content.Context
import com.boxma.gamer.fastfinger.data.storage.StoragePrefs
import com.boxma.gamer.fastfinger.data.storage.StoragePrefs.currentLife
import com.boxma.gamer.fastfinger.data.storage.StoragePrefs.maxLife
import com.boxma.gamer.fastfinger.data.storage.StoragePrefs.score
import javax.inject.Inject

class Repository @Inject constructor(context: Context) {

    private val prefs = StoragePrefs.defaultPref(context)

    fun updateScore(value: Int) { prefs.score = prefs.score + value }
    fun getScore() = prefs.score
    fun removeScore() { prefs.score = 0 }

    fun updateMaxLife(value: Int) { prefs.maxLife = value }     // TODO: 25.02.2022 сделать платным увеличение максимального числа жизней

    fun getCurrentLife() = prefs.currentLife
    fun getMaxLife() = prefs.maxLife
    fun minusCurrentLife(value: Int) { prefs.currentLife = prefs.currentLife - value }
    fun plusCurrentLife(value: Int) { prefs.currentLife = prefs.currentLife + value }
    fun refreshCurrentLife () { prefs.currentLife = prefs.maxLife }
}

