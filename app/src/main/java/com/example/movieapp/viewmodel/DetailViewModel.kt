package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.api.ApiConfig
import com.example.movieapp.data.response.DetailResponse
import com.example.movieapp.data.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailMoview = MutableLiveData<DetailResponse>()
    val detailMovie: MutableLiveData<DetailResponse> = _detailMoview

    fun getDetailMovie(movieId:Int){
        val client = ApiConfig.getApiService().getDetailMovie(movieId)
        client.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        _detailMoview.value=responseBody!!
                    }
                }else{
                    Log.d(TAG,"notSuccess : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d(TAG,"OnFailure : ${t.message}")
            }
        })
    }
    companion object{
        const val TAG = "DetailViewModel"
    }
}