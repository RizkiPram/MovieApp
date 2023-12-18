package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.remote.response.ProductionCompaniesItem
import com.example.movieapp.databinding.ItemProductionCompanyBinding

class ProductionCompanyAdapter(private val data: ArrayList<ProductionCompaniesItem>) :
    RecyclerView.Adapter<ProductionCompanyAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemProductionCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun itemBind(data: ProductionCompaniesItem) {
            binding.apply {
                Glide.with(itemView)
                    .load("${itemView.resources.getString(R.string.image_url)}${data.logoPath}")
                    .into(imgLogo)
                tvCompanyName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductionCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(data[position])
    }
}