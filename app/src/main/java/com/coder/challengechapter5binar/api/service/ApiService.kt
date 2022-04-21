package com.coder.challengechapter5binar.api.service

import com.coder.challengechapter5binar.api.model.DetailMovieResponse
import com.coder.challengechapter5binar.api.model.PopularMovieResponse
import com.coder.challengechapter5binar.api.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/upcoming?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getUpcomingMovie(): Call<UpcomingMoviesResponse>

    @GET("movie/popular?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getPopularMovie(): Call<PopularMovieResponse>

    @GET("movie/{movie_id}?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getMovieById(@Path("movie_id") movie_id: Int): Call<DetailMovieResponse>
}