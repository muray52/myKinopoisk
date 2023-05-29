package com.vostrikov.mykinopoisk.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo() {

    // Let's use this fun to handle api errors
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): ResponseResourse<T> {

        // Returning api response wrapped in ResponseResource class
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Log.d(LOG_CONNECTION_SUCCESS, response.message().toString())
                    ResponseResourse.Success(
                        body = response.body()!!,
                        headers = response.headers()
                    )
                } else {
                    Log.e(LOG_CONNECTION_ERROR, response.errorBody().toString())
                    ResponseResourse.Error(errorMessage = response.errorBody().toString())
                }
            } catch (e: HttpException) {
                // Returning HttpException's message
                Log.e(LOG_CONNECTION_ERROR, e.message.toString())
                ResponseResourse.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                Log.e(LOG_CONNECTION_ERROR, "IOException")
                ResponseResourse.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case of unknown error
                Log.e(LOG_CONNECTION_ERROR, "Unexpected exception")
                ResponseResourse.Error(errorMessage = "Something went wrong")
            }
        }
    }

    companion object {
        private const val LOG_CONNECTION_ERROR = "CONNECTION_ERROR"
        private const val LOG_CONNECTION_SUCCESS = "CONNECTION_SUCCESS"
    }
}
