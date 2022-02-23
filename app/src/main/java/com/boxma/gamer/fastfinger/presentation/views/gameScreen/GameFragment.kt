package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.boxma.gamer.fastfinger.R
import com.boxma.gamer.fastfinger.core.BaseFragment
import com.boxma.gamer.fastfinger.databinding.FragmentGameBinding
import com.boxma.gamer.fastfinger.utils.DisplayMetrics

@SuppressLint("SetTextI18n")
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: GameFragmentViewModel by viewModels()
    private var widthItem = 200
    private var heightItem = 200
    private var speedItem = 4000L
    private var score = 0

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentGameBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {

            currentSecondGame.observe(viewLifecycleOwner) {
                binding.textTimer.text = it.toString()
            }

            itemCount.observe(viewLifecycleOwner) {
                // TODO: тут нужно генерировать падающие элементы
                if(viewModel.isGame.value!!){
                    createItem()
                }
            }

            isEndLevel.observe(viewLifecycleOwner) {
                if (it) {
                    binding.textTimer.text = "Game Over!"
                }
            }

            isGame.observe(viewLifecycleOwner) {
                if (it) {
                    binding.startGame.visibility = GONE
                    binding.textTimer.visibility = VISIBLE
                } else {
                    binding.startGame.visibility = VISIBLE
                    binding.textTimer.visibility = GONE
                    binding.gameField.removeAllViews()
                }
            }
        }
    }

    @SuppressLint("Recycle")
    private fun createItem() {

        val params = LinearLayout.LayoutParams(widthItem, heightItem).apply {
            marginStart = randomMarginStart()
        }

        val imageView = ImageView(requireContext()).apply {
            setImageResource(R.drawable.orc)
            layoutParams = params
            alpha = 1f
        }

        binding.gameField.addView(imageView)

        val translateYAnimation : ObjectAnimator = ObjectAnimator.ofFloat(
            imageView, "translationY", 0f, (DisplayMetrics.displayHeight(requireActivity()) - heightItem).toFloat()
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
                binding.gameField.removeView(imageView)
            }
        })

        imageView.setOnClickListener {
            binding.gameField.removeView(imageView)
            score += 10
            updateScore(score)
            animatorSet.cancel()
        }
    }

    private fun updateScore(score: Int) {
        binding.textScore.text = "Score : $score"

    }

    private fun randomMarginStart() : Int = (8..(DisplayMetrics.displayWidth(requireActivity()) - widthItem)).random()

    private fun initUI() {

        updateScore(score)

        with(binding) {

            startGame.setOnClickListener {
                viewModel.startGame()
                score = 0
                updateScore(0)
            }
        }
    }
}