package com.example.carbon_footprint_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TipsActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips)

        supportActionBar?.hide()

        tvTitle = findViewById(R.id.textViewDetails)
        tvText = findViewById(R.id.textViewText)


        val item = intent.getStringExtra("item")
        tvTitle.text = item

        tvText.text = item?.let { readAsset(it) }
    }

    private fun readAsset(fileName: String): String {
        var fileName = fileName
        val text = application.assets.open("$fileName.txt").bufferedReader().use {
            it.readText()
        }
        return text
    }
}