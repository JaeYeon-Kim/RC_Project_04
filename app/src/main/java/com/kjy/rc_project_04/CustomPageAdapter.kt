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
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}

class Holder(val binding: ItemPageBinding): RecyclerView.ViewHolder(binding.root) {
        var imageView = binding.vpMainPage

}