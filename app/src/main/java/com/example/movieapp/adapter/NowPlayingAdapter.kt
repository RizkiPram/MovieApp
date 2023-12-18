package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.local.entity.MovieEntity
import com.example.movieapp.databinding.ItemNowPlayingMovieBinding

class NowPlayingAdapter(private val list: ArrayList<MovieEntity>) :
    RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(private val binding: ItemNowPlayingMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun itemBind(data: MovieEntity) {
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }
                if (!data.adult) {
                    labelRate.setBackgroundColor(itemView.resources.getColor(R.color.green))
                    tvRate.text = "12+"
                }
                Glide.with(itemView)
                    .load("${itemView.resources.getString(R.string.image_url)}${data.posterPath}")
                    .into(imgMovie)
                tvTitleMovie.text = data.title
                tvDate.text = data.releaseDate
                tvRating.text = "Rating : ${data.voteAverage}/10"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNowPlayingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(list[position])
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieEntity)
    }
}