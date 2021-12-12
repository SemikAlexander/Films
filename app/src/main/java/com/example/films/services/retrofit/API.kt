package com.example.films.services.retrofit

import com.example.films.services.retrofit.filmsDataClasses.FilmsDataClasses
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class Wrapper(
    val results: List<FilmsDataClasses>,
    val result: FilmsDataClasses
)

interface API {
    @GET("movie/{id}")
    suspend fun getFilmInfo(
        @Path("id") id: Int,
        @Query("api_key") key: String = apiToken
    ) : FilmsDataClasses

    @GET("movie/popular")
    suspend fun getPopularFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Wrapper

    @GET("movie/latest")
    fun getLatestFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : FilmsDataClasses

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