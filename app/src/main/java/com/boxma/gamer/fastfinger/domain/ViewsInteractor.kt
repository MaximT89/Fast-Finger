package com.boxma.gamer.fastfinger.domain

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.*
import com.boxma.gamer.fastfinger.R
import com.boxma.gamer.fastfinger.data.Repository
import javax.inject.Inject

enum class ItemsType {
    BOMB,
    FRUIT
}

@SuppressLint("SetTextI18n", "ResourceType")
class ViewsInteractor @Inject constructor(
    private val repository: Repository,
    private val displayMetricsInteractor: DisplayMetricsInteractor
) {

    var callBackRemoveHeart: (() -> Unit)? = null

    private var widthItem = 200
    private var heightItem = 200
    private var speedItem = 4000L

    fun createItem(activity: Activity, parentView: RelativeLayout, textScore: TextView) {

        when (generateItem()) {
            1 -> createFruit(
                activity,
                parentView,
                textScore,
                displayMetricsInteractor.randomMarginStart(activity, widthItem)
            )
            2 -> createBomb(
                activity,
                parentView,
                textScore,
                displayMetricsInteractor.randomMarginStart(activity, widthItem)
            )
            else -> createFruit(
                activity,
                parentView,
                textScore,
                displayMetricsInteractor.randomMarginStart(activity, widthItem)
            )
        }
    }

    private fun createFruit(
        activity: Activity,
        parentView: RelativeLayout,
        textScore: TextView,
        randomMarginStart: Int,
    ) {

        val imageView = createImageView(activity, R.drawable.item_2, randomMarginStart).also {
            parentView.addView(it)
        }

        val animatorSet = createAnimatorSet(activity, heightItem, speedItem, imageView).apply {
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    parentView.removeView(imageView)
                }
            })
        }

        imageView.setOnClickListener {
            parentView.removeView(imageView)
            itemSound(activity, ItemsType.FRUIT)
            repository.updateScore(scorePerItem())      // TODO: вынести отсюда во viewModel
            updateTextScore(textScore)      // TODO: вынести отсюда в обсервер во фрагменте
            animatorSet.cancel()
        }
    }

    private fun createBomb(
        activity: Activity,
        parentView: RelativeLayout,
        textScore: TextView,
        randomMarginStart: Int
    ) {
        val imageView = createImageView(activity, R.drawable.item_bomb, randomMarginStart).also {
            parentView.addView(it)
        }

        val animatorSet = createAnimatorSet(activity, heightItem, speedItem, imageView).apply {
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    parentView.removeView(imageView)
                }
            })
        }

        imageView.setOnClickListener {
            parentView.removeView(imageView)
            itemSound(activity, ItemsType.BOMB)
            animatorSet.cancel()
            callBackRemoveHeart?.invoke()
        }
    }

    private fun createAnimatorSet(
        activity: Activity,
        heightItem: Int,
        speedItem: Long,
        imageView: ImageView
    ): AnimatorSet {
        val translateYAnimation = ObjectAnimator.ofFloat(
            imageView,
            "translationY",
            0f,
            (displayMetricsInteractor.displayHeight(activity) - heightItem).toFloat()
        ).apply {
            repeatCount = 0
        }

        return AnimatorSet().apply {
            duration = speedItem
            playTogether(translateYAnimation)
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun createImageView(activity: Activity, pictureSource: Int, randomMarginStart: Int) =
        ImageView(activity).apply {
            setImageResource(pictureSource)
            layoutParams = getLayoutParam(randomMarginStart)
            alpha = 1f
        }

    private fun generateItem() = when ((1..100).random()) {
        in 1..80 -> 1
        in 81..100 -> 2
        else -> 1
    }

    fun updateTextScore(textScore: TextView) {
        textScore.text = "Score : ${repository.getScore()}"
    }

    private fun itemSound(context: Context, item: ItemsType) {
        val sound: Int = when (item) {
            ItemsType.BOMB -> R.raw.get_damage_2
            ItemsType.FRUIT -> R.raw.get_item
        }

        val mp: MediaPlayer = MediaPlayer.create(context, sound)
        mp.start()
        mp.setOnCompletionListener(MediaPlayer::release)
    }

    // TODO: 25.02.2022 сделать класс где будет описана логика прироста очков
    private fun scorePerItem(): Int = 10

    fun createLifes(activity: Activity, layout: LinearLayout) {

        for (i in 1..repository.getCurrentLife()) {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val imageView = ImageView(activity).apply {
                setImageResource(R.drawable.heart_2)
                layoutParams = params
                id = 100 + i
                alpha = 1f
            }

            layout.addView(imageView)
        }
    }

    fun getIdHeart() = 100 + repository.getCurrentLife()

    fun removeAllHeart(layout: LinearLayout) {
        layout.removeAllViews()
    }

    private fun getLayoutParam(randomMarginStart: Int) =
        LinearLayout.LayoutParams(widthItem, heightItem).apply {
            marginStart = randomMarginStart
        }

    fun removeHeart(layout: LinearLayout, view: View) {
        layout.removeView(view)
    }

    fun addHeart(activity: Activity, layout: LinearLayout) {

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val imageView = ImageView(activity).apply {
            setImageResource(R.drawable.heart_2)
            layoutParams = params
            id = 100 + repository.getCurrentLife() + 1
            alpha = 1f
        }

        layout.addView(imageView)
    }

    fun showDialog(activity: Activity) {

        val dialog = Dialog(activity, R.style.CustomDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.fragment_custom)
            show()
        }
        val body = dialog.findViewById(R.id.btn_start_game) as Button
        body.text = "Start game"
        val yesBtn = dialog.findViewById<Button>(R.id.btn_yes)
        val noBtn = dialog.findViewById(R.id.btn_no) as Button
        yesBtn.setOnClickListener { dialog.dismiss() }
        noBtn.setOnClickListener { dialog.dismiss() }
    }
}