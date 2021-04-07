package com.ldg.notdrunk.main.drink

import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ldg.notdrunk.R
import com.ldg.notdrunk.database.Cup
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.main.drink.callback.ChangeCupCallBack

class CupSelectAdapter
    (//call back on select cup
     private val changeCupCallBack: ChangeCupCallBack)
    : RecyclerView.Adapter<CupSelectAdapter.ViewHolder>() {

    var cupList= listOf<Cup>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val  cupImageView:ImageView = itemView.findViewById(R.id.ivCupImage)
        fun bindView(cup:Cup){
            Glide.with(itemView)
                .load(cup.imageLink)
                .override(600,850)
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .into(cupImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_cup,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(cupList[position])
        holder.itemView.setOnClickListener { view ->
            changeCupCallBack.changeCup(position)
        }

    }


    override fun getItemCount(): Int =cupList.size
}