package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.NowPlayingAdapter
import com.example.movieapp.adapter.UpcomingAdapter
import com.example.movieapp.data.response.ResultsItem
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getListNowPlayingMovie()
        viewModel.getListUpcomingMovie()
        viewModel.listUpcomingMovie.observe(this) {
            setUpcomingMoviesData(it)
        }
        viewModel.listNowPlayingMovie.observe(this) {
            setNowPlayingMoviesData(it)
        }
    }

    private fun setNowPlayingMoviesData(data: List<ResultsItem>) {
        val listMovie = ArrayList<ResultsItem>()
        data.forEach {
            listMovie.add(it)
        }
        binding.rvNowPlaying.apply {
            val nowPlayingAdapter = NowPlayingAdapter(listMovie)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = nowPlayingAdapter
            nowPlayingAdapter.setOnItemClickCallback(object :
                NowPlayingAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ResultsItem) {
                    val intent = Intent(this@MainActivity, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.MOVIE_ID, data.id)
                    startActivity(intent)
                }
            })
        }
    }

    private fun setUpcomingMoviesData(data: List<ResultsItem>) {
        val listMovie = ArrayList<ResultsItem>()
        data.forEach {
            listMovie.add(it)
        }
        binding.rvUpcoming.apply {
            adapter = UpcomingAdapter(listMovie)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}