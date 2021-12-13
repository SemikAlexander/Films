package com.example.films.ui.infoFilm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.films.services.usecase.ListFilmsUseCaseFlow
import com.example.films.ui.listFilms.InfoFilmViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class InfoFilmViewModelFactory @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val listFilmsUseCaseFlow: ListFilmsUseCaseFlow
) : ViewModelProvider.Factory  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass != InfoFilmViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        return InfoFilmViewModel(
            coroutineScope,
            listFilmsUseCaseFlow
        ) as T
    }
}