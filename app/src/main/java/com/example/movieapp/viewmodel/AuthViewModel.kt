package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.datastore.UserPreferences
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: UserPreferences) : ViewModel() {
    fun saveUser(state:Boolean) {
        viewModelScope.launch {
            pref.saveState(state)
        }
    }
    fun getUser(): LiveData<Boolean> {
        return pref.getState().asLiveData()
    }
    fun logout(){
        viewModelScope.launch {
            pref.logout()
        }
    }
}
class AuthViewModelFactory(private val pref: UserPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}