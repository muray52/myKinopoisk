package com.example.mykinopoisk.data.api

import com.example.mykinopoisk.data.api.model.FilmDescriptionApiModel
import com.example.mykinopoisk.data.api.model.LoginInfoApiModel
import com.example.mykinopoisk.data.api.model.LoginRequestApiModel
import com.example.mykinopoisk.data.api.model.TopFilmsPagesApiModel
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Headers(
        "Content-Type: application/json"
    )
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopRateFilms(
        @Header("X-API-KEY") apiKey: String,
        @Query("type") type: String = "QUERY_TYPE_TOP_100",
        @Query("page") page: Int
    ): TopFilmsPagesApiModel

    @Headers(
        "Content-Type: application/json"
    )
    @GET("/api/v2.2/films/{FilmId}")
    suspend fun getFilmDetailedDescription(
        @Header("X-API-KEY") apiKey: String,
        @Path("FilmId") filmId: Int
    ): Response<FilmDescriptionApiModel>

    @POST("/api/v1/auth/login")
    suspend fun postAuth(
        @Body loginRequest: LoginRequestApiModel
    ): Response<Unit>

    @Headers(
        "Content-Type: application/json"
    )
    @GET("/api/v1/users/me")
    suspend fun getXApiKey(
        @Header("Authorization") bearer: String
    ): Response<LoginInfoApiModel>
}