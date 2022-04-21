package com.coder.challengechapter5binar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coder.challengechapter5binar.api.model.DetailMovieResponse
import com.coder.challengechapter5binar.api.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()

    fun getMovieById(id: Int) {
        ApiClient.instance.getMovieById(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                detailMovie.postValue(response.body())
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.d("DetailViewModel", "${t.message}")
            }

        })
    }
}