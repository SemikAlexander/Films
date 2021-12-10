package com.example.films.ui.listFilms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.films.services.presenter.ListFilmsUseCaseFlow
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ListFilmViewModelFactory @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val listFilmsUseCaseFlow: ListFilmsUseCaseFlow
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass != ListFilmsViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        return ListFilmsViewModel(
            coroutineScope,
            listFilmsUseCaseFlow
        ) as T
    }
}