package com.kjy.rc_project_04

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kjy.rc_project_04.databinding.ItemRecyclerBinding

class CustomAdapter: RecyclerView.Adapter<CustomHolder>() {

    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {

        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomHolder(binding)
    }


    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val memo = listData.get(position)


    }

    override fun getItemCount(): Int {
        return listData.size

    }

}


class CustomHolder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
}