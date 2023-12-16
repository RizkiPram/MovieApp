package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.response.ResultsItem
import com.example.movieapp.databinding.ItemUpcomingMovieBinding

class UpcomingAdapter (private val list : ArrayList<ResultsItem>) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemUpcomingMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun itemBind(data:ResultsItem){
            binding.apply {
                if (!data.adult){
                    labelRate.setBackgroundColor(itemView.resources.getColor(R.color.green))
                    tvRate.text="12+"
                }
                Glide.with(itemView)
                    .load("${itemView.resources.getString(R.string.image_url)}${data.backdropPath}")
                    .into(imgUpcomingMovie)
                tvTitleUpcomingMoview.text=data.originalTitle
                tvOverviewUpcomingMoview.text=data.overview
                tvReleasDateUpcomingMovie.text=data.releaseDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpcomingMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(list[position])
    }
}