package com.boxma.gamer.fastfinger.data.storage

import android.content.SharedPreferences
import com.boxma.gamer.fastfinger.core.BaseSharedPreferences

object StoragePrefs : BaseSharedPreferences {

    private const val PROFILE_SCORE = "profile_score"

    var SharedPreferences.score
        get() = getInt(PROFILE_SCORE, 0)
        set(value) = editMe { it.put(PROFILE_SCORE to value) }

}