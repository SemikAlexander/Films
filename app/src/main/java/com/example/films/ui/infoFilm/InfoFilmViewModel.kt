package com.example.films.ui.listFilms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.core.Status
import com.example.films.core.ViewState
import com.example.films.core.convertToFlowViewState
import com.example.films.services.presenter.ListFilmsUseCaseFlow
import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class InfoFilmViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val listFilmsUseCaseFlow: ListFilmsUseCaseFlow
) : ViewModel() {
    val actionState = MutableLiveData<ViewState<FilmsDataClasses>>()

    fun getFilmInfo(id: Int) {
        listFilmsUseCaseFlow.getFilmDetailInfo(id).convertToFlowViewState()
            .onStart { actionState.value = ViewState(Status.LOADING) }
            .onEach { actionState.value = it }
            .catch { actionState.value = ViewState(Status.ERROR, error = it) }
            .launchIn(coroutineScope)
    }
}