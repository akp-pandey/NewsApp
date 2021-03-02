package com.example.news.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import com.example.news.R

class CategoryActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val category1:CardView=findViewById(R.id.category1)

    }

    fun callTechnology(view: View) {

        val intent=Intent(this, TopHeadlineActivity::class.java)
        intent.putExtra("value",1)
        startActivity(intent)
    }

    fun callEntertainment(view: View) {

        val intent=Intent(this, TopHeadlineActivity::class.java)
        intent.putExtra("value",2)
        startActivity(intent)
    }
    fun callSports(view: View) {

        val intent=Intent(this, TopHeadlineActivity::class.java)
        intent.putExtra("value",3)
        startActivity(intent)
    }
    fun callBusiness(view: View) {

        val intent=Intent(this, TopHeadlineActivity::class.java)
        intent.putExtra("value",4)
        startActivity(intent)
    }
    fun callHealth(view: View) {

        val intent=Intent(this, TopHeadlineActivity::class.java)
        intent.putExtra("value",6)
        startActivity(intent)
    }

    fun callScience(view: View) {

        val intent=Intent(this, TopHeadlineActivity::class.java)
        intent.putExtra("value",5)
        startActivity(intent)
    }
}