package com.boxma.gamer.fastfinger.domain

import android.app.Activity
import android.graphics.Point
import javax.inject.Inject

class DisplayMetricsInteractor @Inject constructor() {

    fun randomMarginStart(activity: Activity, widthItem: Int) =
        (8..(displayWidth(activity) - widthItem)).random()

    private fun displayWidth(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun displayHeight(activity: Activity): Int {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

}