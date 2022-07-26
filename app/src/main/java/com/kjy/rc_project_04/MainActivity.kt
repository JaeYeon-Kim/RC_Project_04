package com.kjy.rc_project_04

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kjy.rc_project_04.databinding.ActivityMainBinding
import com.kjy.rc_project_04.databinding.DialogCustomBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val context = this

    private val hotelList = mutableListOf<CheckData>()

    private val pensionList: MutableList<WeekendData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뷰 페이저 불러오기 함수
        addViewPager()

        // 호텔 더보기 커스텀 불러오기 함수
        firstAddButtonCustom()

        // 펜션 더보기 커스텀 불러오기 함수
        secondAddButtonCustom()

        // 호텔 리사이클러뷰 불러오기 함수
        hotelRecycler()

        // 펜션 리사이클러뷰 불러오기 함수
         pensionRecycler()

        // 펜션추가하기(커스텀 다이얼로그)
        binding.addPensionBtn.setOnClickListener {
            val dialog = CustomDialog(this)
            dialog.showDialog()
            dialog.setOnClickListener(object: CustomDialog.ButtonClickListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onClick(titleText: String, priceText: String) {
                    val informPension = WeekendData(R.drawable.add_pension_image_1, titleText, "9.0",
                                                    priceText, "원")
                    pensionList.add(informPension)
                    binding.weekendHotRv.adapter?.notifyDataSetChanged()
                    Toast.makeText(context, "펜션이 추가되었습니다!!", Toast.LENGTH_SHORT).show()
                }


            })
        }

    }

    private fun addViewPager() {
        var imageList: Array<Int> = arrayOf(R.drawable.viewpage_1, R.drawable.viewpage_2,
                                            R.drawable.viewpage_3, R.drawable.viewpage_4,
                                            R.drawable.viewpage_5, R.drawable.viewpage_6,
                                            R.drawable.viewpage_7)

        var customAdapter = CustomPageAdapter(imageList)

        customAdapter.images = imageList

        binding.viewPager.adapter = customAdapter

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

    // 인기 펜션 더보기 버튼 커스텀
    private fun secondAddButtonCustom() {

        // 인기 펜션 더보기 변수
        val textPopular = binding.weekendHotPensionAddBtn
        // 문자열 데이터 취득
        val textData: String = textPopular.text.toString()

        val builder = SpannableStringBuilder(textData)

        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val colorSpan = ForegroundColorSpan(Color.rgb(23, 114, 223))
        builder.setSpan(colorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val colorSpan_second = ForegroundColorSpan(Color.rgb(38, 114, 195))
        builder.setSpan(colorSpan_second, 3, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 바꾼것 적용하기
        textPopular.text = builder

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun hotelRecycler() {
        var adapter = CustomCheckInAdapter(this)
        val hotelList: MutableList<CheckData> = mutableListOf()
        binding.checkInRv.adapter = adapter

        hotelList.apply {
            add(CheckData(
                    R.drawable.hotel_image_1, "[반짝특가]", "아르떼리조트 스파&풀빌라",
                    "9.3", "299,000", "원"))
            add(CheckData(
                    R.drawable.hotel_image_2, "[반짝특가]", "롯데리조트 속초",
                    "9.6", "557,000", "원"))
            add(CheckData(
                    R.drawable.hotel_image_3, "[반짝특가]", "호메르스 호텔",
                    "8.9", "99,900", "원"))
            add(CheckData(
                    R.drawable.hotel_image_4, "[반짝특가]", "라마다 속초",
                    "9.2", "237,000", "원"))
            add(CheckData(
                    R.drawable.hotel_image_5, "[반짝특가]", "스카이베이호텔 경포",
                    "9.0", "300,500", "원"))
            add(CheckData(
                    R.drawable.hotel_image_6, "[반짝특가]", "호텔 소사이어티",
                    "9.2", "99,900", "원"))
            add(CheckData(
                    R.drawable.hotel_image_7, "[반짝특가]", "신라스테이 서초",
                    "9.2", "134,000", "원"))
        }
        adapter.listData = hotelList
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.checkInRv.layoutManager = layoutManager
        adapter.notifyDataSetChanged()


    }

    private fun pensionRecycler() {
        var adapter = CustomHotAdapter(this)
        binding.weekendHotRv.adapter = adapter


        // 각 이미지 텍스트들 리스트에 넣어주기
        pensionList.apply{
            add(
                WeekendData(R.drawable.pension_image_1, "홍천 푸름펜션", "9.3",
                                "250,000", "원")
            )
            add(WeekendData(R.drawable.pension_image_2, "거제 하와유 풀빌라&스파펜션", "8.8",
                "92,070", "원"))
            add(WeekendData(R.drawable.pension_image_3, "경주 몽블랑 펜션", "8.1",
                "93,000", "원"))
            add(WeekendData(R.drawable.pension_image_4, "안면도 달이머무는바다 펜션", "9.8",
                "202,000", "원"))
            add(WeekendData(R.drawable.pension_image_5, "태안 밀키블루펜션", "9.8",
                "158,193", "원"))
            add(WeekendData(R.drawable.pension_image_6, "여수 지뜨펜션", "9.4",
                "167,400", "원"))
            add(WeekendData(R.drawable.pension_image_7, "태안 엘마르펜션", "8.5",
                "126,480", "원"))

            add(WeekendData(R.drawable.pension_image_8, "창원 힐링타임", "8.5",
                "276,000", "원"))
        }

        adapter.pensionList = pensionList
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.weekendHotRv.layoutManager = layoutManager

    }

}