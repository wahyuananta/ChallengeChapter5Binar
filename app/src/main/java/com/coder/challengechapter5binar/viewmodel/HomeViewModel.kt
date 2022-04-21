package com.coder.challengechapter5binar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coder.challengechapter5binar.api.model.PopularMovieResponse
import com.coder.challengechapter5binar.api.model.UpcomingMoviesResponse
import com.coder.challengechapter5binar.api.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    val upcomingMovie: MutableLiveData<UpcomingMoviesResponse> = MutableLiveData()
    val popularMovie: MutableLiveData<PopularMovieResponse> = MutableLiveData()

    fun getUpcomingMovie() {
        ApiClient.instance.getUpcomingMovie().enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onResponse(
                call: Call<UpcomingMoviesResponse>,
                response: Response<UpcomingMoviesResponse>
            ) {
                response.body()?.let {
                    upcomingMovie.postValue(it)
                }
            }

            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                Log.d("UpcomingViewModel", "${t.message}")
            }

        })
    }

    fun getPopularMovie() {
        ApiClient.instance.getPopularMovie().enqueue(object : Callback<PopularMovieResponse> {
            override fun onResponse(
                call: Call<PopularMovieResponse>,
                response: Response<PopularMovieResponse>
            ) {
            response.body()?.let {
                popularMovie.postValue(it)
                }
            }

            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                Log.d("PopularViewModel", "${t.message}")
            }

        })
    }
}