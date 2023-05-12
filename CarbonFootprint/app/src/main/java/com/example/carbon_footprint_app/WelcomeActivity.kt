package com.example.carbon_footprint_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class WelcomeActivity : AppCompatActivity() {

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.hide()

        val time = 2000
        startTimer(time)
    }

    private fun startTimer(TIME_IN_MILLIS: Int){
        countDownTimer = object: CountDownTimer(TIME_IN_MILLIS.toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                startMainActivity()
            }

        }.start()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}