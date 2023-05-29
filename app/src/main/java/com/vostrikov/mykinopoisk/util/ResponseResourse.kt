package com.vostrikov.mykinopoisk.util

sealed class ResponseResourse<T>(
    val body: T? = null,
    val headers: okhttp3.Headers? = null,
    val message: String? = null
) {

    // We'll wrap our data in this 'Success'
    // class in case of success response from api
    class Success<T>(body: T, headers: okhttp3.Headers) : ResponseResourse<T>(body = body, headers = headers)

    // We'll pass error message wrapped in this 'Error'
    // class to the UI in case of failure response
    class Error<T>(errorMessage: String) : ResponseResourse<T>(message = errorMessage)

    // We'll just pass object of this Loading
    // class, just before making an api call
    class Loading<T> : ResponseResourse<T>()
}