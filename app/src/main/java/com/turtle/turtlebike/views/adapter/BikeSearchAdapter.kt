package com.turtle.turtlebike.views.adapter

import android.app.Activity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turtle.turtlebike.R
import com.turtle.turtlebike.databinding.ItemBikeSearchBinding
import com.turtle.turtlebike.model.UBikeModel
import com.turtle.turtlebike.model.UBikeModelItem

class BikeSearchAdapter(private var activity: Activity) : RecyclerView.Adapter<BikeSearchAdapter.BikeSearchAdapter>() {

    private var lists: UBikeModel = UBikeModel()
    private var searchStr = ""

    inner class BikeSearchAdapter(var binding: ItemBikeSearchBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(model: UBikeModelItem) {

            with(binding) {

                if (searchStr.isNotEmpty()) {
                    tvTwo.text = highlightSearchTerm(model.sarea, searchStr)
                    tvThree.text = highlightSearchTerm(model.sna, searchStr)
                } else {
                    tvTwo.text = model.sarea
                    tvThree.text = model.sna
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeSearchAdapter {

        val itemView = ItemBikeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BikeSearchAdapter(itemView)

    }

    override fun onBindViewHolder(holder: BikeSearchAdapter, position: Int) {

        holder.bind(
            lists.get(position) ?: UBikeModelItem()
        )

    }

    override fun getItemCount(): Int = lists.size ?: 0

    fun updateData(newList: UBikeModel, searchStr: String) {
        Log.d("TAG", "checkpointewfjeqi newList = ${newList}")
        this.searchStr = searchStr
        lists = newList
        notifyDataSetChanged()

    }

    private fun highlightSearchTerm(text: String, searchTerm: String): SpannableString {
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf(searchTerm, ignoreCase = true)
        if (startIndex >= 0) {
            val endIndex = startIndex + searchTerm.length
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(activity, R.color.bike_main_color)),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    fun finalSearchCheck(newList: UBikeModel, searchStr: String) {

        this.searchStr = searchStr
        lists = if (searchStr.isEmpty()) {
            newList // 如果 searchStr 是空的，顯示全部數據
        } else {
            UBikeModel().apply {
                // 只添加完全符合 searchStr 的項目
                addAll(newList.filter {
                    it.sarea.equals(searchStr, ignoreCase = true) || it.sna.equals(searchStr, ignoreCase = true)
                })
            }
        }

        notifyDataSetChanged()

    }

}