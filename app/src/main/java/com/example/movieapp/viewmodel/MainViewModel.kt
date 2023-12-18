package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieapp.repository.MovieRepository

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getNowPlayingMovie() = movieRepository.getNowPlayingMovie()
    fun getListUpcomingMovie() = movieRepository.getUpComingMovie()

    companion object {
        const val TAG = "MainViewModel"
    }
}