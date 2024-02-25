package com.zobaze.zobazerefractortask.ui.data

sealed class UiData<T> {
    class Success<T>(val data: T) : UiData<T>()
    class Loading<T> : UiData<T>()
    class Failure<T>(val errorMessage: String, val retry: () -> Unit) : UiData<T>()

    val bindingData: T?
        get() = if (this is Success) {
            data
        } else {
            null
        }

    fun getFailure(): Failure<T>? {
        return this as? Failure
    }

    val isSuccess: Boolean
        get() = this is Success

    val isLoading: Boolean
        get() = this is Loading

    val isFailure: Boolean
        get() = this is Failure
}