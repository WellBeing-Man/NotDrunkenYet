package com.ldg.notdrunk.main.drink

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ldg.notdrunk.R
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.main.drink.callback.ChangeDrinkCallBack

class DrinkSelectAdapter(
    private val drinkList: List<Drink>,
    private val changeDrinkCallBack: ChangeDrinkCallBack
) : RecyclerView.Adapter<DrinkSelectAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val drinkImageView:ImageView = itemView.findViewById(R.id.ivDrinkImage)
        fun bindView(drink:Drink){
            Glide.with(itemView)
                .load(drink.imageLink)
                .override(600,850)
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .into(drinkImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_drink,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(drinkList[position])
        holder.itemView.setOnClickListener { view->
            changeDrinkCallBack.changeDrink(position)
        }

    }


    override fun getItemCount(): Int =drinkList.size


}