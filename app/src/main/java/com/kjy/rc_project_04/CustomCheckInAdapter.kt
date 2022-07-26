package com.kjy.rc_project_04

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kjy.rc_project_04.databinding.ItemCheckinBinding
import kotlin.coroutines.coroutineContext

// 어댑터 구현
class CustomCheckInAdapter(context: Context): RecyclerView.Adapter<CustomCheckInAdapter.CustomHolder>() {

    var listData = mutableListOf<CheckData>()

    private val cContext = context

    inner class CustomHolder(val binding: ItemCheckinBinding): RecyclerView.ViewHolder(binding.root) {

        fun setCheck(checkData: CheckData) {

            Glide.with(cContext)
                .load(checkData.uri)
                .centerCrop()
                .into(binding.checkMainImage)
            binding.discountTitle.text = checkData.discountTitle
            binding.hotelTitle.text = checkData.hotelTitle
            binding.starScoreText.text = checkData.starText
            binding.priceText.text = checkData.priceText
            binding.defaultWonText.text = checkData.wonText

        }

    }

    // 한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val binding = ItemCheckinBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CustomHolder(binding)
    }

    // 생성된 아이템 레이아웃에 갑 입력후 목록에 출력
    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val checkData = listData[position]
        holder.setCheck(checkData)

    }

    // 목록에 보여줄 아이템 개수
    override fun getItemCount(): Int {
        return listData.size
    }
}

