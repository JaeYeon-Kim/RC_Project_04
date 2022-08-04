package com.kjy.rc_project_04

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kjy.rc_project_04.databinding.ItemPageBinding

class CustomPageAdapter(var images : Array<Int>): RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 페이지를 넘김에 따라 position은 늘어나지만 해당 뷰페이저 페이지 수로 나눔으로써 배너를 반복해서 사용할 수 있다.
        holder.imageView.setImageResource(images[position % 7] )
    }

    override fun getItemCount(): Int {
        // 무한으로 보여주는 효과를 위해 Int값의 최대를 리턴해줌.
        return Int.MAX_VALUE
    }
}

class Holder(val binding: ItemPageBinding): RecyclerView.ViewHolder(binding.root) {
        var imageView = binding.vpMainPage

}