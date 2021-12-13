package com.example.films.services.usecase

import android.util.Log
import com.example.films.core.UseCaseDispatchers
import com.example.films.services.retrofit.API
import com.example.films.services.retrofit.filmsDataClasses.Film
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ListFilmsUseCaseFlow @Inject constructor(
    private val dispatcherProvider: UseCaseDispatchers
) {

    private val filmsAPI = API.api

    fun getFilmDetailInfo(idFilm: Int): Flow<Film> {
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

    suspend fun getListFilms(language: String, page: Int): List<Film> = try {
        filmsAPI.getPopularFilms(language = language, page = page).results
    } catch (e: Exception) {
        Log.e("api", "getLatestFilms", e)
        throw e
    }

    suspend fun getListFilmsSearch(language: String, page: Int, name: String): List<Film> = try {
        filmsAPI.getPopularFilmsSearch(language = language, page = page, name = name).results
    } catch (e: Exception) {
        Log.e("api", "getLatestFilms", e)
        throw e
    }

    private suspend fun getFilm(id: Int): Film = try {
        filmsAPI.getFilmInfo(id = id)
    } catch (e: Exception) {
        Log.e("api", "getFilmInfoError", e)
        throw e
    }
}