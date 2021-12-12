package com.example.films.services.presenter

import android.util.Log
import com.example.films.core.UseCaseDispatchers
import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses
import com.example.films.services.retrofit.API
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListFilmsUseCaseFlow @Inject constructor(
    private val dispatcherProvider: UseCaseDispatchers
) {

    private val filmsAPI = API.api

    fun getPopularFilms(language: String, page: Int): Flow<List<FilmsDataClasses>> {
        try {
            return flow {
                emit(getListFilms(language, page))
            }
                .flowOn(dispatcherProvider.ioDispatcher)
                .map { it.toList() }
        } catch (e: Exception) {
            Log.e("api", "getLatestFilms", e)
            throw e
        }
    }

    fun getFilmDetailInfo(idFilm: Int): Flow<FilmsDataClasses> {
        try {
            return flow {
                emit(getFilm(idFilm))
            }
                .flowOn(dispatcherProvider.ioDispatcher)
        } catch (e: Exception) {
            Log.e("api", "getFilmInfo", e)
            throw e
        }
    }

    private suspend fun getListFilms(language: String, page: Int): List<FilmsDataClasses> = try {
        filmsAPI.getPopularFilms(language = language, page = page).results
    } catch (e: Exception) {
        Log.e("api", "getLatestFilms", e)
        throw e
    }

    private suspend fun getFilm(id: Int): FilmsDataClasses = try {
        filmsAPI.getFilmInfo(id = id)
    } catch (e: Exception) {
        Log.e("api", "getFilmInfoError", e)
        throw e
    }
}