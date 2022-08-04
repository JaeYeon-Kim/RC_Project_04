package com.kjy.rc_project_04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.kjy.rc_project_04.databinding.ActivityFirstSplashBinding
import splitties.activities.start

class FirstSplashActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityFirstSplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        var lottieImage = binding.lottieAnimView

        lottieImage.playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            start<SecondSplashActivity>()
            finish()
        },2000)
    }
}