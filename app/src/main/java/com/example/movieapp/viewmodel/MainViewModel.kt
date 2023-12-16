package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.api.ApiConfig
import com.example.movieapp.data.response.MovieResponse
import com.example.movieapp.data.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listNowPlayingMovie = MutableLiveData<List<ResultsItem>>()
    val listNowPlayingMovie:MutableLiveData<List<ResultsItem>> = _listNowPlayingMovie
    private val _listUpcomingMovie = MutableLiveData<List<ResultsItem>>()
    val listUpcomingMovie:MutableLiveData<List<ResultsItem>> = _listUpcomingMovie
    fun getListUpcomingMovie(){
        val client = ApiConfig.getApiService().getUpcomingMovie()
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        _listUpcomingMovie.value=responseBody.results
                    }
                }else{
                    Log.d(TAG,"NotSuccess : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG,"OnFailure ${t.message}")
            }
        })
    }
    fun getListNowPlayingMovie(){
        val client = ApiConfig.getApiService().getNowPlayingMovie()
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        _listNowPlayingMovie.value=responseBody.results
                    }
                }else{
                    Log.d(TAG,"NotSuccess : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG,"OnFailure ${t.message}")
            }
        })
    }
    companion object{
        const val TAG = "MainViewModel"
    }
}