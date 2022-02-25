package com.boxma.gamer.fastfinger.presentation.views.gameScreen

import android.os.Bundle
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
import com.boxma.gamer.fastfinger.domain.LifesInteractor
import com.boxma.gamer.fastfinger.domain.ViewsInteractor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding>()  {

    private val viewModel: GameFragmentViewModel by viewModels()

    @Inject lateinit var repository: Repository
    @Inject lateinit var viewsInteractor: ViewsInteractor
    @Inject lateinit var lifesInteractor: LifesInteractor

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
                if(viewModel.isGame.value!!){
                    viewsInteractor.createItem(requireActivity(), binding.gameField, binding.textScore)
                }
            }

            isEndLevel.observe(viewLifecycleOwner) {
                if (it) {
                    // TODO: 24.02.2022 тут нужна логика когда уровень закончен
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

    private fun initUI() {

        viewsInteractor.updateTextScore(binding.textScore)
        viewsInteractor.callBackRemoveHeart = { removeHeart() }

        with(binding) {

            startGame.setOnClickListener {

                lifesInteractor.refreshCurrentLife()
                viewsInteractor.removeAllHeart(fieldLife)
                viewsInteractor.createLifes(requireActivity(), fieldLife)

                viewModel.startGame()
                repository.removeScore()
                viewsInteractor.updateTextScore(textScore)
            }

            btnDeleteHeart.setOnClickListener {
                removeHeart()
            }
        }
    }

    private fun removeHeart(){
        view?.findViewById<ImageView>(viewsInteractor.getIdHeart())?.let {
            viewsInteractor.removeHeart(binding.fieldLife, it)
        }
    }


}