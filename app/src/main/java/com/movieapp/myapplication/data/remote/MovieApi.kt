package com.movieapp.myapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {
    companion object{

        const val BASE_URL="https://api.themoviedb.org/3/"
        const val API_KEY= ""
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(
        @Query("page") position:Int
    ) : MovieResponse

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovies(
        @Query("query") query:String="nature",
        @Query("page") position:Int
    ) : MovieResponse
}