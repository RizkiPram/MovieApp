package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapter.ProductionCompanyAdapter
import com.example.movieapp.data.response.ProductionCompaniesItem
import com.example.movieapp.databinding.ActivityDetailMovieBinding
import com.example.movieapp.viewmodel.DetailViewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailMovieBinding
    private val viewModel by viewModels<DetailViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId=intent.getIntExtra(MOVIE_ID,0)
        binding.imgBack.setOnClickListener { onBackPressed() }
        viewModel.getDetailMovie(movieId)
        viewModel.detailMovie.observe(this){
            binding.apply {
                Glide.with(this@DetailMovieActivity)
                    .load("${resources.getString(R.string.image_url)}${it.posterPath}")
                    .into(imgPosterMovie)
                Glide.with(this@DetailMovieActivity)
                    .load("${resources.getString(R.string.image_url)}${it.backdropPath}")
                    .into(imgBackdrop)
                tvTitle.text=it.title
                tvOverview.text=it.overview
                tvStatus.text=it.status
                tvTagline.text=it.tagline
                if (it.genres.isNotEmpty()){
                    it.genres.forEach {
                        val tv = TextView(this@DetailMovieActivity)
                        val builder = StringBuilder()
                        builder.append(it.name).append(" ")
                        tv.text=builder.toString()
                        layoutGenre.addView(tv)
                    }
                }
                if (it.productionCompanies.isNotEmpty()){
                    setProductionCompanyData(it.productionCompanies)
                }
            }
        }
    }
    private fun setProductionCompanyData(data:List<ProductionCompaniesItem>){
        val listCompany = ArrayList<ProductionCompaniesItem>()
        data.forEach {
            listCompany.add(it)
        }
        binding.rvProductionCompany.apply {
            adapter=ProductionCompanyAdapter(listCompany)
            layoutManager=LinearLayoutManager(this@DetailMovieActivity)
        }
    }
    companion object{
        const val MOVIE_ID="movie_id"
    }
}