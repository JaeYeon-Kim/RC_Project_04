package com.kjy.rc_project_04

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kjy.rc_project_04.databinding.ItemPlayBinding
import com.squareup.picasso.Picasso

class CustomPlayAdapter: RecyclerView.Adapter<CustomHolder>() {

    var playList = mutableListOf<PlayData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val binding = ItemPlayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        var playData = playList[position]
        holder.playSet(playData)
    }

    override fun getItemCount(): Int {
        return playList.size
    }

}

class CustomHolder(val binding: ItemPlayBinding): RecyclerView.ViewHolder(binding.root) {
    fun playSet(playData: PlayData) {
        Picasso.get()
            .load(playData.uri)
            .into(binding.playRvItemImage)
        binding.playRvItemText.text = playData.playTitleText

    }

}