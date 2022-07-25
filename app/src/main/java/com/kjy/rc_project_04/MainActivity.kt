package com.kjy.rc_project_04

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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


        var imageList: Array<Int> = arrayOf(R.drawable.viewpage_1, R.drawable.viewpage_2 , R.drawable.viewpage_3,
            R.drawable.viewpage_4, R.drawable.viewpage_5, R.drawable.viewpage_6, R.drawable.viewpage_7)

        var customAdapter = CustomPageAdapter(imageList)

        customAdapter.images = imageList

        binding.viewPager.adapter = customAdapter

        firstAddButtonCustom()


    }

    // 특가 호텔 더보기 버튼 커스텀
    private fun firstAddButtonCustom() {
        // 특가 호텔 더보기 버튼의 특가 글씨 바꿔보기(특정 문자열 변경)
        val textDiscount = binding.discountHotelAddBtn
        // 문자열 데이터 취득
        val textData: String = textDiscount.text.toString()

        //SpannableStringBuilder로 변환
        val builder = SpannableStringBuilder(textData)
        // 볼드체를 적용
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val colorSpan = ForegroundColorSpan(Color.rgb(23, 114, 223))
        builder.setSpan(colorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val colorSpan_second = ForegroundColorSpan(Color.rgb(38, 114, 195))
        builder.setSpan(colorSpan_second, 3, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 바꾼것 적용하기
        textDiscount.text = builder

    }

    // 인기 펜션 더보기 버튼 커슬텀
    private fun secondAddButtonCustom() {

    }

}