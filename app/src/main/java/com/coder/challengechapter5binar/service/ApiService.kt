package com.coder.challengechapter5binar.service

import com.coder.challengechapter5binar.model.popularmovie.DetailPopularMovieResponse
import com.coder.challengechapter5binar.model.popularmovie.PopularMovieResponse
import com.coder.challengechapter5binar.model.popularmovie.TopRatedMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/top_rated?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getTopRatedMovie(): Call<TopRatedMovieResponse>

    @GET("movie/popular?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getPopularMovie(): Call<PopularMovieResponse>

    @GET("movie/{movie_id}?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getMovieById(@Path("movie_id") movie_id: Int): Call<DetailPopularMovieResponse>
}