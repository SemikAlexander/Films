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

class ListFilmsViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val listFilmsUseCaseFlow: ListFilmsUseCaseFlow
) : ViewModel() {
    val actionState = MutableLiveData<ViewState<List<FilmsDataClasses>>>()
    val filmActionState = MutableLiveData<ViewState<FilmsDataClasses>>()

    fun getListFilms(language: String, page: Int) {
        listFilmsUseCaseFlow.getLatestFilms(language, page).convertToFlowViewState()
            .onStart { actionState.value = ViewState(Status.LOADING) }
            .onEach { actionState.value = it }
            .catch { actionState.value = ViewState(Status.ERROR, error = it) }
            .launchIn(coroutineScope)
    }

    fun getFilmInfo(id: Int) {
        listFilmsUseCaseFlow.getFilmDetailInfo(id).convertToFlowViewState()
            .onStart { filmActionState.value = ViewState(Status.LOADING) }
            .onEach { filmActionState.value = it }
            .catch { filmActionState.value = ViewState(Status.ERROR, error = it) }
            .launchIn(coroutineScope)
    }
}