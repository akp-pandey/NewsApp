package com.example.news.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.news.R


//Adding BindingAdapter for loading of image
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView,url:String?)
{
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.news).into(imageView)
}