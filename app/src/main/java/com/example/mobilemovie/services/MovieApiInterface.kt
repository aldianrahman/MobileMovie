package com.example.mobilemovie.services

import com.example.mobilemovie.models.ComingResponse
import com.example.mobilemovie.models.MovieResponse
import com.example.mobilemovie.models.RankResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("/3/movie/popular?api_key=b8344cb11b1112f5b64098c48b4e1b7d")
    fun getMovieList(@Query("page")page:Int) : retrofit2.Call<MovieResponse>

//    @GET("movie/popular")
//    fun getPopularMovies(@Query("page") page: Int): retrofit2.Call<MoviesResponse?>?

    @GET("/3/movie/top_rated?api_key=b8344cb11b1112f5b64098c48b4e1b7d")
    fun getRankList() : retrofit2.Call<RankResponse>

    @GET("/3/movie/upcoming?api_key=b8344cb11b1112f5b64098c48b4e1b7d")
    fun getComingList() : retrofit2.Call<ComingResponse>

    @GET("/3/search/movie?api_key=b8344cb11b1112f5b64098c48b4e1b7d&query=")
    fun getSearch() : retrofit2.Call<ComingResponse>


}