package com.boxma.gamer.fastfinger.presentation.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.boxma.gamer.fastfinger.core.BaseActivity
import com.boxma.gamer.fastfinger.databinding.ActivityGameBinding
import com.boxma.gamer.fastfinger.presentation.views.gameScreen.GameFragmentViewModel

class GameActivity : BaseActivity<ActivityGameBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityGameBinding =
        ActivityGameBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}