package com.boxma.gamer.fastfinger.domain

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.boxma.gamer.fastfinger.R
import com.boxma.gamer.fastfinger.data.Repository
import com.boxma.gamer.fastfinger.utils.DisplayMetrics
import javax.inject.Inject

@SuppressLint("SetTextI18n")
class ViewsInteractor @Inject constructor(private val repository: Repository) {

    private var widthItem = 200
    private var heightItem = 200
    private var speedItem = 4000L

    fun createItem(activity: Activity, parentView : RelativeLayout, textScore: TextView) {

        fun randomMarginStart() : Int = (8..(DisplayMetrics.displayWidth(activity) - widthItem)).random()

        val params = LinearLayout.LayoutParams(widthItem, heightItem).apply {
            marginStart = randomMarginStart()
        }

        val imageView = ImageView(activity).apply {
            setImageResource(R.drawable.item_2)
            layoutParams = params
            alpha = 1f
        }

        parentView.addView(imageView)

        val translateYAnimation : ObjectAnimator = ObjectAnimator.ofFloat(
            imageView, "translationY", 0f, (DisplayMetrics.displayHeight(activity) - heightItem).toFloat()
        ).apply {
            repeatCount = 0
        }

        val animatorSet = AnimatorSet().apply {
            duration = speedItem
            playTogether(translateYAnimation)
            interpolator = LinearInterpolator()
            start()
        }

        animatorSet.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                parentView.removeView(imageView)
            }
        })

        imageView.setOnClickListener {
            parentView.removeView(imageView)
            repository.updateScore(10)
            updateScore(textScore)
            animatorSet.cancel()
        }
    }

    fun updateScore(textScore: TextView){
        textScore.text = "Score : ${repository.getScore()}"
    }
}