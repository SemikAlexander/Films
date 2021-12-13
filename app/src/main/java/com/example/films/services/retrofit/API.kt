package com.example.films.services.retrofit

import com.example.films.services.retrofit.filmsDataClasses.Film
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class Wrapper(
    val results: List<Film>
)

interface API {
    @GET("movie/{id}")
    suspend fun getFilmInfo(
        @Path("id") id: Int,
        @Query("api_key") key: String = apiToken
    ) : Film

    @GET("movie/popular")
    suspend fun getPopularFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Wrapper

    @GET("search/movie")
    suspend fun getPopularFilmsSearch(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") name: String
    ) : Wrapper


    @GET("movie/{id}")
    suspend fun searchFilm(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("include_adult") adult: Boolean = false,
        @Path("query") name: String
    ) : Wrapper

    @GET("movie/latest")
    fun getLatestFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Film

    @GET("movie/top_rated")
    fun getTopRatedFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<Film>

    @GET("movie/upcoming")
    fun getUpcomingFilms(
        @Query("api_key") key: String = apiToken,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : List<Film>

    companion object {
        val api by lazy { retrofit.create<API>() }
    }
}