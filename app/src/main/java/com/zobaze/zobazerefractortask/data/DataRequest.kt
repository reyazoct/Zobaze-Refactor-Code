package com.zobaze.zobazerefractortask.data


sealed class DataRequest<T> {
    data class Success<T>(val data: T) : DataRequest<T>()
    data class Failure<T>(val throwable: Throwable) : DataRequest<T>()
}