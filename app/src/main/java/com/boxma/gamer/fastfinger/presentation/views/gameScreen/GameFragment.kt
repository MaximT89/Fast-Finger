package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.boxma.gamer.fastfinger.core.BaseFragment
import com.boxma.gamer.fastfinger.databinding.FragmentGameBinding
import com.boxma.gamer.fastfinger.utils.DisplayMetrics

@SuppressLint("SetTextI18n")
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel : GameFragmentViewModel by viewModels()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentGameBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel){

            currentSecondGame.observe(this@GameFragment, {
                binding.testText.text = it.toString()
            })

            isEndLevel.observe(this@GameFragment, {
                if (it){
                    binding.testText.text = "Game Over!"
                }
            })


        }
    }

    private fun initUI() {
        with(binding){
            testText.text = "displaySize: ${DisplayMetrics.displayWidth(requireActivity())} and ${DisplayMetrics.displayHeight(requireActivity())}"

            startGame.setOnClickListener {
                viewModel.startGame()
            }

            pauseGame.setOnClickListener {
                viewModel.pauseGame()
            }
        }
    }
}