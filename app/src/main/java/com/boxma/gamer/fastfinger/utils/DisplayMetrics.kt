package com.boxma.gamer.fastfinger.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Point

class DisplayMetrics {

    companion object{
        @SuppressLint("NewApi")
        fun displayWidth(activity: Activity) : Int {
            val display = activity.windowManager.defaultDisplay

//            val display1 = activity.windowManager.currentWindowMetrics
//            display1.bounds.width()
//            display1.bounds.height()

            val size = Point()
            display.getSize(size)
            return size.x
        }

        fun displayHeight(activity: Activity) : Int {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.y
        }
    }
}