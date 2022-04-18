package com.coder.challengechapter5binar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.coder.challengechapter5binar.databinding.ActivityMainBinding
import com.coder.challengechapter5binar.model.popularmovie.PopularMovieResponse
import com.coder.challengechapter5binar.model.popularmovie.Result
import com.coder.challengechapter5binar.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchAllData()
    }

    private fun fetchAllData() {
        ApiClient.instance.getPopularMovie()
            .enqueue(object : Callback<PopularMovieResponse> {
                override fun onResponse(
                    call: Call<PopularMovieResponse>,
                    response: Response<PopularMovieResponse>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200) {
                        if (response.isSuccessful) {
                            body?.let { showList(it.results) }
                        }
                        binding.pbLoading.visibility = View.GONE
                    } else {
                        binding.pbLoading.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                    binding.pbLoading.visibility = View.GONE
                }
            })
    }

    private fun showList(data: List<Result>?) {
        val adapter = MainAdapter(object : MainAdapter.OnClickListener {
            override fun onClickItem(data: Result) {

            }
        })

        adapter.submitData(data)
        binding.rvList.adapter = adapter
    }
}