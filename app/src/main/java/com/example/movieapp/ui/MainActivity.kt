package com.example.movieapp.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.NowPlayingAdapter
import com.example.movieapp.adapter.UpcomingAdapter
import com.example.movieapp.data.local.datastore.UserPreferences
import com.example.movieapp.data.local.entity.MovieEntity
import com.example.movieapp.data.local.entity.UpcomingMovieEntity
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.utils.Result
import com.example.movieapp.viewmodel.AuthViewModel
import com.example.movieapp.viewmodel.AuthViewModelFactory
import com.example.movieapp.viewmodel.MainViewModel
import com.example.movieapp.viewmodel.ViewModelFactory
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: MainViewModel by viewModels {
            factory
        }
        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(UserPreferences.getInstance(dataStore))
        )[AuthViewModel::class.java]
        viewModel.getNowPlayingMovie().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val movieData = result.data
                        setNowPlayingMoviesData(movieData)
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@MainActivity,
                            "Oops Something Wrong",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        viewModel.getListUpcomingMovie().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val movieData = result.data
                        setUpcomingMoviesData(movieData)
                    }

                    is Result.Error -> {}
                }
            }
        }
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

    }

    private fun setNowPlayingMoviesData(data: List<MovieEntity>) {
        val listMovie = ArrayList<MovieEntity>()
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
                override fun onItemClicked(data: MovieEntity) {
                    moveToDetailPage(data.id)
                }
            })
        }
    }

    private fun setUpcomingMoviesData(data: List<UpcomingMovieEntity>) {
        val listMovie = ArrayList<UpcomingMovieEntity>()
        data.forEach {
            listMovie.add(it)
        }
        binding.rvUpcoming.apply {
            val upcomingMovieAdapter = UpcomingAdapter(listMovie)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMovieAdapter
            upcomingMovieAdapter.setOnItemClickCallback(object :
                UpcomingAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UpcomingMovieEntity) {
                    moveToDetailPage(data.id)
                }
            })
        }
    }

    private fun moveToDetailPage(id: Int) {
        val intent = Intent(this@MainActivity, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.MOVIE_ID, id)
        startActivity(intent)
    }
    private fun showLogoutDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_dialog)
        dialog.window?.setDimAmount(0.8f)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.findViewById<TextView>(R.id.tvDesc).text = "Are You Sure Want to Logout?"
        dialog.findViewById<MaterialButton>(R.id.btnYes)?.setOnClickListener {
            authViewModel.logout()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        dialog.findViewById<MaterialButton>(R.id.btnNo)?.setOnClickListener {
            dialog.cancel()
        }
    }
}