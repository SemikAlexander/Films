package com.example.films.core

class ViewState<T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {

    fun isSuccess() = status == Status.SUCCESS

    fun isLoading() = status == Status.LOADING

    fun getErrorMessage() = error?.message

    fun shouldShowErrorMessage() = error != null && status == Status.ERROR
}