package com.neyogiry.android.sample.pokedex.domain

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val failure: Failure) : Result<Nothing>()
}

sealed class Failure {
    data object DataError : Failure()
    data object ServerError : Failure()
    data object UnknownError: Failure()
}