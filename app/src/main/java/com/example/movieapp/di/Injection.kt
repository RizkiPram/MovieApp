package com.example.movieapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.movieapp.data.local.datastore.UserPreferences
import com.example.movieapp.data.local.room.MovieDatabase
import com.example.movieapp.data.remote.api.ApiConfig
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val apiService = ApiConfig.getApiService()
        val database = MovieDatabase.getInstance(context)
        val dao = database.movieDao()
        val appExecutors = AppExecutors
        return MovieRepository.getInstance(apiService, dao,appExecutors)
    }
}