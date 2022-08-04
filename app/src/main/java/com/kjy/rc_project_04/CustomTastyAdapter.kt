package com.kjy.rc_project_04

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kjy.rc_project_04.databinding.ItemTastyBinding
import com.squareup.picasso.Picasso

class CustomTastyAdapter: RecyclerView.Adapter<CustomTastyAdapter.CustomHolder>() {


    // 데이터 클래스 담아둘 리스트 선언
    var restaurantList = mutableListOf<TastyData>()

    inner class CustomHolder(val binding: ItemTastyBinding): RecyclerView.ViewHolder(binding.root) {
        fun setTasty(tastyData: TastyData) {
            // 피카소 라이브러리로 이미지 넣음
            Picasso
                .get()
                .load(tastyData.uri)
                .into(binding.tastyRvItemImage)
            binding.tastyRvItemText.text = tastyData.tastyTitleText
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val binding = ItemTastyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val tastyData = restaurantList[position]
        holder.setTasty(tastyData)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}

