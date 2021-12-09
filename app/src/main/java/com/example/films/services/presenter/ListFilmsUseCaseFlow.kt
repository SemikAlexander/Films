package com.example.films.services.presenter

import com.example.films.core.UseCaseDispatchers
import com.example.films.services.filmsDataClasses.filmsDataClasses
import com.example.films.services.retrofit.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListFilmsUseCaseFlow @Inject constructor(
    private val dispatcherProvider: UseCaseDispatchers,
    private val filmsAPI: API
    ) {

    fun getPopularFilms(language: String, page: Int): Flow<List<filmsDataClasses>> {
        return flow {
            emit(filmsAPI.getPopularFilms(language = language, page = page))
        }
            .flowOn(dispatcherProvider.ioDispatcher)
            .map { it.toList() }
    }

    fun getLatestFilms(language: String, page: Int): Flow<List<filmsDataClasses>> {
        return flow {
            emit(filmsAPI.getLatestFilms(language = language, page = page))
        }
            .flowOn(dispatcherProvider.ioDispatcher)
            .map { it.toList() }
    }

    fun getTopRatedFilms(language: String, page: Int): Flow<List<filmsDataClasses>> {
        return flow {
            emit(filmsAPI.getTopRatedFilms(language = language, page = page))
        }
            .flowOn(dispatcherProvider.ioDispatcher)
            .map { it.toList() }
    }

    fun getUpcomingFilms(language: String, page: Int): Flow<List<filmsDataClasses>> {
        return flow {
            emit(filmsAPI.getUpcomingFilms(language = language, page = page))
        }
            .flowOn(dispatcherProvider.ioDispatcher)
            .map { it.toList() }
    }
}