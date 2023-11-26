package com.turtle.turtlebike.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turtle.turtlebike.R
import com.turtle.turtlebike.databinding.ItemMenuContentBinding
import com.turtle.turtlebike.model.MenuModel

class MenuAdapter(var context: Context, var modelList: MutableList<MenuModel>, private val itemClick: (MenuModel) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var selectedPosition: Int = 2 //init 選中的位置

    inner class MenuViewHolder(private var binding: ItemMenuContentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MenuModel, position: Int) {

            binding.menuBinding = model

            if (model.isSelected) {

                binding.tvContent.setTextColor(ContextCompat.getColor(context, R.color.bike_main_color_selected))

            } else {

                binding.tvContent.setTextColor(ContextCompat.getColor(context, R.color.white))

            }

            binding.root.setOnClickListener {

                val previousSelectedPosition = selectedPosition
                selectedPosition = position

                //更新選中的位置
                modelList[position].isSelected = true

                //更新之前選中的狀態
                if (previousSelectedPosition >= 0) {
                    modelList[previousSelectedPosition].isSelected = false
                }

                //更新
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(position)

                itemClick(model)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val itemView = ItemMenuContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(itemView)

    }

    override fun getItemCount(): Int = modelList.size ?: 0

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.bind(modelList[position], position)

    }
}