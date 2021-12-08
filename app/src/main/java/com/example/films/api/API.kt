package com.example.films.api

import com.example.films.filmsDataClasses.filmsDataClasses
import retrofit2.Call
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("movie/{id}")
    fun getFilmInfo(
        @Path("id") idFilm: Int,
        @Query("api_key") key: String = apiToken
    ) : Call<filmsDataClasses>

    @GET("movie/popular")
    fun getPopularFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<filmsDataClasses>

    @GET("movie/latest")
    fun getLatestFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<filmsDataClasses>

    @GET("movie/top_rated")
    fun getTopRatedFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<filmsDataClasses>

    @GET("movie/upcoming")
    fun getUpcomingFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<filmsDataClasses>

    companion object {
        val api by lazy { retrofit.create<API>() }
    }
}