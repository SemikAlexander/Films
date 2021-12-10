package com.example.films.services.retrofit

import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("movie/{id}")
    fun getFilmInfo(
        @Path("id") id: Int,
        @Query("api_key") key: String = apiToken
    ) : FilmsDataClasses

    @GET("movie/popular")
    fun getPopularFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<FilmsDataClasses>

    @GET("movie/latest")
    fun getLatestFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<FilmsDataClasses>

    @GET("movie/top_rated")
    fun getTopRatedFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<FilmsDataClasses>

    @GET("movie/upcoming")
    fun getUpcomingFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<FilmsDataClasses>

    companion object {
        val api by lazy { retrofit.create<API>() }
    }
}