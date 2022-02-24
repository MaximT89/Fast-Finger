package com.boxma.gamer.fastfinger.core

import android.content.Context
import android.content.SharedPreferences

interface BaseSharedPreferences  {

    companion object {
        private const val APP_PREF = "app_prefs"
    }

    fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
        val key = pair.first
        when (val value = pair.second) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }

    fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) =
        edit().also(operation).apply()

    fun defaultPref(context: Context): SharedPreferences =
        context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

}