package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.boxma.gamer.fastfinger.R
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
    }

    private fun initUI() {
        binding.testText.text = "displaySize: ${DisplayMetrics.displayWidth(requireActivity())} and ${DisplayMetrics.displayHeight(requireActivity())}"
    }


}