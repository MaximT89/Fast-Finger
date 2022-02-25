package com.boxma.gamer.fastfinger.data.storage

import android.content.SharedPreferences
import com.boxma.gamer.fastfinger.core.BaseSharedPreferences

object StoragePrefs : BaseSharedPreferences {

    private const val SCORE = "score"
    private const val MAX_LIFE = "max_life"
    private const val CURRENT_LIFE = "current_life"

    var SharedPreferences.score
        get() = getInt(SCORE, 0)
        set(value) = editMe { it.put(SCORE to value) }

    var SharedPreferences.maxLife
        get() = getInt(MAX_LIFE, 3)
        set(value) = editMe { it.put(MAX_LIFE to value) }

    var SharedPreferences.currentLife
        get() = getInt(CURRENT_LIFE, 0)
        set(value) = editMe { it.put(CURRENT_LIFE to value) }

}