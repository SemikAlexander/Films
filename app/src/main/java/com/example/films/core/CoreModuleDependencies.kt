package com.example.films.core

import com.example.films.services.presenter.ListFilmsUseCaseFlow
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoreModuleDependencies {
    fun getListFilms(): ListFilmsUseCaseFlow
}