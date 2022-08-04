package com.kjy.rc_project_04

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.ScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.kjy.rc_project_04.databinding.ActivityMainBinding
import com.romainpiel.shimmer.Shimmer
import splitties.toast.toast

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // 배너 갯수
    private var numViewpager = 7        // 배너 광고 갯수

    // 무한 뷰페이저를 위한 현재 포지션을 정해줌
    // 좌우로 전부 무한 스크롤이 가능해야하기 때문에 중간을 현재 포지션으로 지정해줌
    private var currentPosition = Int.MAX_VALUE / 2

    // 스레드 사용을 위한 핸들러 클래스 변수 지정
    private var myHandler = MyHandler()

    // 페이지 간격 시간을 설정함
    private val intervalTime = 4000.toLong()


    private val context = this

    private val hotelList = mutableListOf<CheckData>()

    private val pensionList = mutableListOf<WeekendData>()

    private val restaurantList = mutableListOf<TastyData>()

    private val playList = mutableListOf<PlayData>()


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

        // 메인 타이틀 애니메이션 주기
        titleAnimation()

        // 취향대로 맛집어때 리사이클러뷰 불러오기 함수
        tastyRecycler()


        // 취향대로 놀면어때 리사이클러뷰 불러오기 함수
        playRecycler()

        // 카테고리 형식의 리사이클러뷰의 스크롤을 막아 전체 스크롤뷰의 스크롤이 버벅이는 현상을 없애줌.
        binding.playRv.isNestedScrollingEnabled = false
        binding.tastyRv.isNestedScrollingEnabled = false


        // shimmerText animation
        shimmerTextView()



        // 펜션추가하기(커스텀 다이얼로그)
        binding.addPensionBtn.setOnClickListener {
            val dialog = CustomDialog(this)
            dialog.showDialog()
            dialog.setOnClickListener(object : CustomDialog.ButtonClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onClick(titleText: String, priceText: String) {
                    val informPension = WeekendData(
                        R.drawable.add_pension_image_1, titleText, "9.0",
                        priceText, "원"
                    )
                    pensionList.add(informPension)
                    binding.weekendHotRv.adapter?.notifyDataSetChanged()
                    toast("펜션이 추가되었습니다.")
                }
            })
        }

        // 토탈 숫자 먼저 가져옴
        // xml에서 설정되어 있는 text가 String 타입이기 때문에 위에서 설정한 Int타입의 광고 배너 갯수도 String으로 변환 해줌.
        binding.indicatorEntireText.text = numViewpager.toString()

        // 뷰페이저의 현재 위치를 지정한다.
        // true로 지정하면 처음 액티비티 실행시 배너가 맨 처음 배너로 액션이 들어가면서 시작하므로 꺼줘야한다.
        binding.viewPager.setCurrentItem(currentPosition, false)

        // 몇번째 인지 알려주기 위해 리스너 연결
        binding.viewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                // 페이지 텍스트에 맞는 포지션 값
                @SuppressLint("SetTextI18n")
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    // 배너가 7개이므로 해당
                    binding.indicatorCurrentText.text =
                        "${(position % 7) + 1}"    // position이 인덱스 값이라서 0부터 시작이므로 + 1
                }

                // 뷰페이저 스크롤 상태 감지 메소드
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손을 떼었을때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        // 뷰페이저 작동중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }


    }

    private fun addViewPager() {
        var imageList: Array<Int> = arrayOf(
            R.drawable.viewpage_1, R.drawable.viewpage_2,
            R.drawable.viewpage_3, R.drawable.viewpage_4,
            R.drawable.viewpage_5, R.drawable.viewpage_6,
            R.drawable.viewpage_7
        )

        var customAdapter = CustomPageAdapter(imageList)
        customAdapter.images = imageList
        binding.viewPager.adapter = customAdapter

    }

    // 자동 스크롤 함수
    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0)     // 해당 메소드를 미호출시 핸들러가 계속 늘어나게된다 (0, 1, 2) 따라서 호출하고 지워준다.
        myHandler.sendEmptyMessageDelayed(0, intervalTime)

    }

    private fun autoScrollStop() {
        myHandler.removeMessages(0)     // 핸들러를 중지시킴
    }

    // MyHandler라는 스레드를 생성.
    private inner class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            // int what -> 메시지의 의미를 설명 한다. 여기서는 간단하게 what을 통해서 메시지를 보낼 때 사용한다.
            // 핸들러를 통해 전할 메시지(데이터)를 정한다고 생각하면 쉬움
            if (msg.what == 0) {
                binding.viewPager.setCurrentItem(++currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime)   // 스크롤을 계속 이어서 한다. 지정한 intervalTime에 맞춰서
            }
        }

    }

    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
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
        binding.checkInRv.adapter = adapter

        hotelList.apply {
            add(
                CheckData(
                    R.drawable.hotel_image_1, "[반짝특가]", "아르떼리조트 스파",
                    "9.3", "299,000", "원"
                )
            )
            add(
                CheckData(
                    R.drawable.hotel_image_2, "[반짝특가]", "롯데리조트 속초",
                    "9.6", "557,000", "원"
                )
            )
            add(
                CheckData(
                    R.drawable.hotel_image_3, "[반짝특가]", "호메르스 호텔",
                    "8.9", "99,900", "원"
                )
            )
            add(
                CheckData(
                    R.drawable.hotel_image_4, "[반짝특가]", "라마다 속초",
                    "9.2", "237,000", "원"
                )
            )
            add(
                CheckData(
                    R.drawable.hotel_image_5, "[반짝특가]", "스카이베이호텔 경포",
                    "9.0", "300,500", "원"
                )
            )
            add(
                CheckData(
                    R.drawable.hotel_image_6, "[반짝특가]", "호텔 소사이어티",
                    "9.2", "99,900", "원"
                )
            )
            add(
                CheckData(
                    R.drawable.hotel_image_7, "[반짝특가]", "신라스테이 서초",
                    "9.2", "134,000", "원"
                )
            )
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
        pensionList.apply {
            add(
                WeekendData(
                    R.drawable.pension_image_1, "홍천 푸름펜션", "9.3",
                    "250,000", "원"
                )
            )
            add(
                WeekendData(
                    R.drawable.pension_image_2, "거제 하와유 풀빌라&스파펜션", "8.8",
                    "92,070", "원"
                )
            )
            add(
                WeekendData(
                    R.drawable.pension_image_3, "경주 몽블랑 펜션", "8.1",
                    "93,000", "원"
                )
            )
            add(
                WeekendData(
                    R.drawable.pension_image_4, "안면도 달이머무는바다 펜션", "9.8",
                    "202,000", "원"
                )
            )
            add(
                WeekendData(
                    R.drawable.pension_image_5, "태안 밀키블루펜션", "9.8",
                    "158,193", "원"
                )
            )
            add(
                WeekendData(
                    R.drawable.pension_image_6, "여수 지뜨펜션", "9.4",
                    "167,400", "원"
                )
            )
            add(
                WeekendData(
                    R.drawable.pension_image_7, "태안 엘마르펜션", "8.5",
                    "126,480", "원"
                )
            )

            add(
                WeekendData(
                    R.drawable.pension_image_8, "창원 힐링타임", "8.5",
                    "276,000", "원"
                )
            )
        }

        adapter.pensionList = pensionList
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.weekendHotRv.layoutManager = layoutManager

    }

    private fun titleAnimation() {
        YoYo.with(Techniques.Pulse)
            .duration(1500)
            .repeat(1000000)
            .playOn(binding.mainTitle)

        YoYo.with(Techniques.Flash)
            .duration(2000)
            .repeat(1000000)
            .playOn(binding.newBar)
    }

    private fun tastyRecycler() {
        var adapter = CustomTastyAdapter()
        binding.tastyRv.adapter = adapter
        // 리스트에 정보들 넣어주기
        restaurantList.apply {
            add(
                TastyData(
                R.drawable.tasty_image_4, "원할머니 보쌈족발 특가")

            )

            add(
                TastyData(
                R.drawable.tasty_image_2, "조개창고 전국지점 특가"
            )
            )

            add(
                TastyData(R.drawable.tasty_image_1, "뷔페할인은 여기어때"

            ))

            add(TastyData(
                R.drawable.tasty_image_3, "더파티 부산권 대표뷔페"

            ))

        }
        adapter.restaurantList = restaurantList
        val layoutManager = GridLayoutManager(this, 2)
        binding.tastyRv.layoutManager = layoutManager

    }

    private fun playRecycler() {
        var adapter = CustomPlayAdapter()
        binding.playRv.adapter = adapter
        playList.apply {
            add(
                PlayData(R.drawable.play_image1, "365일 테마파크"

            ))

            add(PlayData(R.drawable.play_image2, "아이랑 키즈카페"

            ))

            add(PlayData(R.drawable.play_image3, "제주도 핫플레이스"

            ))

            add(PlayData(R.drawable.play_image4, "워터파크 선착순쿠폰"

            ))
        }
        adapter.playList = playList
        var layoutManager = GridLayoutManager(this, 2)
        binding.playRv.layoutManager = layoutManager
    }

    private fun shimmerTextView() {

        val shimmerText1 = binding.tastyTitle1
        val shimmerText2 = binding.tastyTitle2
        val shimmerText3 = binding.playTitle1
        val shimmerText4 = binding.playTitle2
        val shimmerText5 = binding.weekTitle1
        val shimmerText6 = binding.weekTitle2
        val shimmerText7 = binding.checkInTitle1
        val shimmerText8 = binding.checkInTitle2
        val shimmerText9 = binding.themeTitle1
        val shimmerText10 = binding.themeTitle2

        val shimmer = Shimmer()

        shimmer.apply {
            start(shimmerText1)
            start(shimmerText2)
            start(shimmerText3)
            start(shimmerText4)
            start(shimmerText5)
            start(shimmerText6)
            start(shimmerText7)
            start(shimmerText8)
            start(shimmerText9)
            start(shimmerText10)
        }
    }

}