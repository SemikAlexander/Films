package com.example.films.core

import com.example.films.services.usecase.ListFilmsUseCaseFlow
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreModuleDependencies {
    fun getListFilms(): ListFilmsUseCaseFlow
}

@InstallIn(SingletonComponent::class)
@Module(includes = [])
class CoreModule {

    @Singleton
    @Provides
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    @Provides
    fun provideUseCaseDispatchers(): UseCaseDispatchers {
        return UseCaseDispatchers(Dispatchers.IO, Dispatchers.Default, Dispatchers.Main)
    }

}
