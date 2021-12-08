package com.example.films.filmsAPI

import com.example.films.filmsDataClasses.filmsDataClasses
import retrofit2.Call
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("movie/{id}")
    fun getFilmInfo(
        @Path("id") id: Int,
        @Query("api_key") key: String = apiToken
    ) : Call<filmsDataClasses>



    companion object {
        val api by lazy { retrofit.create<API>() }
    }
}