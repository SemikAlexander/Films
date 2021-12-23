package com.example.films.ui.listFilms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.films.services.usecase.ListFilmsUseCaseFlow
import com.example.films.services.retrofit.filmsDataClasses.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ListFilmsViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val listFilmsUseCaseFlow: ListFilmsUseCaseFlow
) : ViewModel() {

    var search = ""

    val suggestions: StateFlow<PagingData<Film>> = Pager(PagingConfig(pageSize = 20)) {
        FilmsDataSource(listFilmsUseCaseFlow, "en-US", search)
    }.flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}

class FilmsDataSource(
    private val listFilmsUseCaseFlow: ListFilmsUseCaseFlow,
    private val language: String,
    private val search: String
) : PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(20)

        return try {

            val response = if (search.isEmpty()) {
                listFilmsUseCaseFlow.getListFilms(language, page)
            } else {
                listFilmsUseCaseFlow.getListFilmsSearch(language, page, search)
            }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}