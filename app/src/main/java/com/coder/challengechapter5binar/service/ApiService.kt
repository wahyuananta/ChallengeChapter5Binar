package com.coder.challengechapter5binar.service

import com.coder.challengechapter5binar.model.popularmovie.PopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular?api_key=d75df6abb480d067ab096c09ffbf1082")
    fun getPopularMovie(): Call<PopularMovieResponse>
}