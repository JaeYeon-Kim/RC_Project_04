package com.kjy.rc_project_04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjy.rc_project_04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var adapter = CustomAdapter()

        var imageList: Array<Int> = arrayOf(R.drawable.viewpage_1, R.drawable.viewpage_2 , R.drawable.viewpage_3,
            R.drawable.viewpage_4, R.drawable.viewpage_5, R.drawable.viewpage_6, R.drawable.viewpage_7)

        var customAdapter = CustomPageAdapter(imageList)

        customAdapter.images = imageList

        binding.viewPager.adapter = customAdapter


    }

}