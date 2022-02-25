package com.boxma.gamer.fastfinger.utils

import android.app.Activity
import android.graphics.Point

class DisplayMetrics {

    companion object{

        fun displayWidth(activity: Activity) : Int {
            val display = activity.windowManager.defaultDisplay
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