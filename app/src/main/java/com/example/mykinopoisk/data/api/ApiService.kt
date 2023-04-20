package com.example.mykinopoisk.data.api

import androidx.lifecycle.LiveData
import com.example.mykinopoisk.data.api.model.FilmDescriptionApiModel
import com.example.mykinopoisk.data.api.model.TopFilmsPagesApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @Headers(
        "X-API-KEY: $KEY_API",
        "Content-Type: application/json"
    )
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopRateFilms(
        @Query("type") type: String = "QUERY_TYPE_TOP_100",
        @Query("page") page: Int
    ): TopFilmsPagesApiModel

    @Headers(
        "X-API-KEY: $KEY_API",
        "Content-Type: application/json"
    )
    @GET("/api/v2.2/films/{FilmId}")
    suspend fun getFilmDetailedDescription(@Path("FilmId") filmId: Int): FilmDescriptionApiModel

    companion object {
        //token for kinopoisk API connection
        private const val KEY_API = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }
}