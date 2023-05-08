package com.example.mykinopoisk.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo() {

    // we'll use this function in all
    // repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): ResponseResourse<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    ResponseResourse.Success(body = response.body()!!, headers = response.headers()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
//                    val errorResponse: ExampleErrorResponse? = convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    Log.e("CONNECTION_ERROR", response.errorBody().toString())
                    ResponseResourse.Error(errorMessage = response.errorBody().toString())
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Log.e("CONNECTION_ERROR", e.message.toString())
                ResponseResourse.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Log.e("CONNECTION_ERROR", "IOException")
                ResponseResourse.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Log.e("CONNECTION_ERROR", "Unexpected exception")
                ResponseResourse.Error(errorMessage = "Something went wrong")
            }
        }
    }
}
