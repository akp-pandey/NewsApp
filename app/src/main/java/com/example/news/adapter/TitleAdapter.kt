package com.example.news.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.NewsTitleLayoutBinding
import com.example.news.modules.DetailNewsActivity
import com.example.news.modules.TopHeadlineActivity
import com.example.news.response.Article
import com.example.news.response.DiffArticel

class TitleAdapter(
        private val onClick: (Article) -> Unit,
        var context: TopHeadlineActivity,
        var url: String
):androidx.recyclerview.widget.ListAdapter<Article, TitleAdapter.ArticleViewHolder>(DiffArticel) {
    class ArticleViewHolder(var view: NewsTitleLayoutBinding):RecyclerView.ViewHolder(view.root){
        fun bind(item: Article) {
            view.run {
                this.newsTitle=item
                executePendingBindings()
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding=NewsTitleLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val intent=Intent(context, DetailNewsActivity::class.java)
            intent.putExtra("poistion",holder.adapterPosition)
            intent.putExtra("url",url)
            context.startActivity(intent)
        }
    }

}
