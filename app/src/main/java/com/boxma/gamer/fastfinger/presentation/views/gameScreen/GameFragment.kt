package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.boxma.gamer.fastfinger.core.BaseFragment
import com.boxma.gamer.fastfinger.data.Repository
import com.boxma.gamer.fastfinger.databinding.FragmentGameBinding
import com.boxma.gamer.fastfinger.domain.ViewsInteractor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class GameFragment : BaseFragment<FragmentGameBinding>() {

    private val viewModel: GameFragmentViewModel by viewModels()

    @Inject lateinit var repository: Repository
    @Inject lateinit var viewsInteractor: ViewsInteractor

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
                if (viewModel.isGame.value!!) {
                    viewsInteractor.createItem(
                        requireActivity(),
                        binding.gameField
                    )
                }
            }

            isEndLevel.observe(viewLifecycleOwner) {
                if (it) {
                    // TODO: 24.02.2022 тут нужна логика когда уровень закончен
                }
            }

            isGame.observe(viewLifecycleOwner) {
                if (it) uiStartGame()
                else {
                    viewModel.stopGame()
                    uiFinishGame()
                }
            }

            lifes.observe(viewLifecycleOwner) {
                if (it == 0) {
                    isGame.postValue(false)
                }
            }

            score.observe(viewLifecycleOwner) {
                updateTextScore(it.toString())
            }
        }
    }

    private fun updateTextScore(score: String) {
        binding.textScore.text = "Score : $score"
    }

    private fun uiFinishGame() {
        binding.startGame.visibility = VISIBLE
        binding.textTimer.visibility = GONE
        binding.gameField.removeAllViews()
    }

    private fun uiStartGame() {
        binding.startGame.visibility = GONE
        binding.textTimer.visibility = VISIBLE
    }

    private fun initUI() {

        updateTextScore(repository.getScore().toString())
        viewsInteractor.callBackRemoveHeart = {
            removeHeart()
            viewModel.minusLife()
        }

        viewsInteractor.callBackUpdateScore = { viewModel.updateScore(viewsInteractor.scorePerItem()) }

        with(binding) {

            startGame.setOnClickListener {
                viewModel.startGame()

                viewsInteractor.removeAllHeart(fieldLife)
                viewsInteractor.createLifes(requireActivity(), fieldLife)

                repository.removeScore()
                updateTextScore(repository.getScore().toString())
            }

            btnDeleteHeart.setOnClickListener {

                viewsInteractor.showDialog(requireActivity())

                viewModel.stopGame()
                uiFinishGame()
            }
        }
    }

    private fun removeHeart() {
        Log.d("TAG", "removeHeart: ${repository.getCurrentLife()}")

        view?.findViewById<ImageView>(viewsInteractor.getIdHeart())?.let {
            viewsInteractor.removeHeart(binding.fieldLife, it)
        }
    }
}