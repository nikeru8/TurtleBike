package com.turtle.turtlebike.views.adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.turtle.turtlebike.R
import com.turtle.turtlebike.databinding.ItemBikeBinding
import com.turtle.turtlebike.model.UBikeModel
import com.turtle.turtlebike.model.UBikeModelItem

class BikeAdapter(private var activity: Activity, private var lists: UBikeModel?) : RecyclerView.Adapter<BikeAdapter.BikeViewHolder>() {


    private var originalLists: UBikeModel? = lists

    inner class BikeViewHolder(var binding: ItemBikeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UBikeModelItem, isEven: Boolean, isLastItem: Boolean) {

            with(binding) {

                tvOne.text = "台北市"
                tvTwo.text = model.sarea ?: ""
                tvThree.text = model.sna ?: ""

                if (isEven) {

                    root.setBackgroundColor(ContextCompat.getColor(activity, R.color.rv_bike_even_color))

                } else {

                    root.setBackgroundColor(ContextCompat.getColor(activity, R.color.rv_bike_odd_color))

                }



            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeViewHolder {

        val itemView = ItemBikeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BikeViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BikeViewHolder, position: Int) {

        holder.bind(
            lists?.get(position) ?: UBikeModelItem(),
            isEven = position % 2 == 0,
            isLastItem = position == itemCount - 1
        )

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(lists: UBikeModel?) {

        this.lists = lists
        originalLists = lists
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = lists?.size ?: 0

    fun filter(text: String): UBikeModel {

        val filteredList = if (text.isEmpty()) {
            originalLists ?: UBikeModel()
        } else {
            val tempList = UBikeModel()
            originalLists?.forEach {
                if (it.sarea.contains(text, ignoreCase = true) || it.sna.contains(text, ignoreCase = true)) {
                    tempList.add(it)
                }
            }
            tempList
        }

        return filteredList
    }


}