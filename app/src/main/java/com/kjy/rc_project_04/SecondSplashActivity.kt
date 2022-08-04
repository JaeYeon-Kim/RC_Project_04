package com.kjy.rc_project_04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import splitties.activities.start

class SecondSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            start<MainActivity>()
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            finish()
        },1000)

    }
}