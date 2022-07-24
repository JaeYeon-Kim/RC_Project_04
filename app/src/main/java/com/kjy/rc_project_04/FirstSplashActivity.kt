package com.kjy.rc_project_04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class FirstSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SecondSplashActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }
}