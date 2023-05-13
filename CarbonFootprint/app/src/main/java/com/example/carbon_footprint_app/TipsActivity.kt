package com.example.carbon_footprint_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class TipsActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvText: TextView
    private lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)

        supportActionBar?.hide()

        tvTitle = findViewById(R.id.textViewDetails)
        tvText = findViewById(R.id.textViewText)
        ivImage = findViewById(R.id.imageViewTips)


        val item = intent.getStringExtra("item")
        tvTitle.text = item

        tvText.text = item?.let { readAsset(it) }

        val modifiedString = item?.lowercase()?.replace(" ", "_").toString()

        val imageResId = resources.getIdentifier(modifiedString, "drawable", packageName)

        Glide.with(this).load(imageResId).into(ivImage)
    }

    private fun readAsset(fileName: String): String {
        var fileName = fileName
        val text = application.assets.open("$fileName.txt").bufferedReader().use {
            it.readText()
        }
        return text
    }
}