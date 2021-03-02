package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.MainPageLayoutBinding
import com.example.news.response.Article
import com.example.news.response.DiffArticel

class MainPageAdapter(private val onlick: (Article)-> Unit): ListAdapter<Article, MainPageAdapter.MainPageViewHolder>(DiffArticel) {
    class MainPageViewHolder(var binding: MainPageLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article?) {
            binding.run {
                this.article=item
                executePendingBindings()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageViewHolder {
      val binding=MainPageLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

}
