package com.kjy.rc_project_04

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kjy.rc_project_04.databinding.ItemWeekendhotBinding
import splitties.toast.toast

class CustomHotAdapter(context: Context): RecyclerView.Adapter<CustomHotAdapter.CustomWeekendHolder>() {

    var pensionList = mutableListOf<WeekendData>()

    private val dContext = context

    inner class CustomWeekendHolder(val binding: ItemWeekendhotBinding): RecyclerView.ViewHolder(binding.root) {
        fun setHot(weekendData: WeekendData) {
            // 펜션 배경 이미지를 불러옴
            Glide
                .with(dContext)
                .load(weekendData.uri)
                .centerCrop()           // 여백없이 꽉 채워줌
                .into(binding.pensionMainImage)
            binding.pensionTitle.text = weekendData.pensionTitle
            binding.pensionScoreText.text = weekendData.pensionScore
            binding.priceText.text = weekendData.priceText
            binding.defaultWonText.text = weekendData.wonText
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomWeekendHolder {

        val binding = ItemWeekendhotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomWeekendHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomWeekendHolder, position: Int) {
        val weekendData = pensionList[position]
        holder.binding.mainPensionView.setOnLongClickListener {
            pensionList.removeAt(position)
            notifyItemRemoved(position)
            toast("해당 펜션이 삭제되었습니다.")
            true
        }
        holder.setHot(weekendData)
    }

    override fun getItemCount(): Int {
        return pensionList.size
    }

}

