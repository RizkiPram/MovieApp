package com.example.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.movieapp.data.local.datastore.UserPreferences
import com.example.movieapp.data.local.entity.MovieEntity
import com.example.movieapp.data.local.entity.UpcomingMovieEntity
import com.example.movieapp.data.local.room.MovieDao
import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.data.remote.response.MovieResponse
import com.example.movieapp.utils.AppExecutors
import com.example.movieapp.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository private constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val appExecutors: AppExecutors,
//    private val pref:UserPreferences
) {

    fun getNowPlayingMovie(): LiveData<Result<List<MovieEntity>>> {
        val result = MediatorLiveData<Result<List<MovieEntity>>>()
        result.value = Result.Loading
        val client = apiService.getNowPlayingMovie()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()?.results
                    val listMovie = ArrayList<MovieEntity>()
                    appExecutors.diskIO.execute {
                        movie?.forEach { movie ->
                            val movieItem = MovieEntity(
                                movie.id,
                                movie.title,
                                movie.posterPath,
                                movie.adult,
                                movie.voteAverage,
                                movie.releaseDate,
                            )
                            listMovie.add(movieItem)
                        }
                        movieDao.insertMovie(listMovie)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = movieDao.getMovies()
        result.addSource(localData) { newData: List<MovieEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getUpComingMovie(): LiveData<Result<List<UpcomingMovieEntity>>> {
        val result = MediatorLiveData<Result<List<UpcomingMovieEntity>>>()
        result.value = Result.Loading
        val client = apiService.getUpcomingMovie()
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()?.results
                    val listMovie = ArrayList<UpcomingMovieEntity>()
                    appExecutors.diskIO.execute {
                        movie?.forEach { movie ->
                            val movieItem = UpcomingMovieEntity(
                                movie.id,
                                movie.title,
                                movie.backdropPath,
                                movie.releaseDate,
                                movie.overview,
                                movie.adult
                            )
                            listMovie.add(movieItem)
                        }
                        movieDao.insertUpComingMovie(listMovie)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = movieDao.getUpComingMovies()
        result.addSource(localData) { newData: List<UpcomingMovieEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            apiService: ApiService,
            movieDao: MovieDao,
            appExecutors: AppExecutors,
//            pref:UserPreferences
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(apiService, movieDao, appExecutors)
            }.also { instance = it }
    }
}