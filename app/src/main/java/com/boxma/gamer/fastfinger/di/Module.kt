package com.boxma.gamer.fastfinger.di

import android.content.Context
import com.boxma.gamer.fastfinger.data.Repository
import com.boxma.gamer.fastfinger.domain.DisplayMetricsInteractor
import com.boxma.gamer.fastfinger.domain.LifesInteractor
import com.boxma.gamer.fastfinger.domain.ViewsInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext context: Context) = Repository(context)

    @Singleton
    @Provides
    fun provideViewsInteractor(repository: Repository, displayMetricsInteractor: DisplayMetricsInteractor) =
        ViewsInteractor(repository, displayMetricsInteractor)

    @Singleton
    @Provides
    fun provideLifesInteractor(repository: Repository) = LifesInteractor(repository)

    @Singleton
    @Provides
    fun provideDisplayMetricsInteractor() = DisplayMetricsInteractor()

}