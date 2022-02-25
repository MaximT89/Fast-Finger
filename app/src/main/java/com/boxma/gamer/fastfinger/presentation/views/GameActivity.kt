package com.boxma.gamer.fastfinger.presentation.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.boxma.gamer.fastfinger.core.BaseActivity
import com.boxma.gamer.fastfinger.databinding.ActivityGameBinding
import com.boxma.gamer.fastfinger.utils.DisplayMetrics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameActivity : BaseActivity<ActivityGameBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityGameBinding =
        ActivityGameBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}