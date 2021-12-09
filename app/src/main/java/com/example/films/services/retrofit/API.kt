package com.example.films.services.retrofit

import com.example.films.services.filmsDataClasses.filmsDataClasses
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("movie/{id}")
    fun getFilmInfo(
        @Path("id") id: Int,
        @Query("api_key") key: String = apiToken
    ) : filmsDataClasses

    @GET("movie/popular")
    fun getPopularFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<filmsDataClasses>

    @GET("movie/latest")
    fun getLatestFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<filmsDataClasses>

    @GET("movie/top_rated")
    fun getTopRatedFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<filmsDataClasses>

    @GET("movie/upcoming")
    fun getUpcomingFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<filmsDataClasses>

    companion object {
        val api by lazy { retrofit.create<API>() }
    }
}