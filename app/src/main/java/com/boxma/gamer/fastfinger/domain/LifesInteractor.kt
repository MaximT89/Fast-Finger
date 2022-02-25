package com.boxma.gamer.fastfinger.domain

import com.boxma.gamer.fastfinger.data.Repository
import javax.inject.Inject

class LifesInteractor @Inject constructor(private val repository: Repository) {

    fun getCurrentLife() = repository.getCurrentLife()
    fun refreshCurrentLife() = repository.refreshCurrentLife()
    fun minusCurrentLife(value : Int) = repository.minusCurrentLife(value)
    fun plusCurrentLife(value : Int) = repository.plusCurrentLife(value)
}