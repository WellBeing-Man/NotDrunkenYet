package com.ldg.notdrunk.main.drink

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.ldg.notdrunk.R
import com.ldg.notdrunk.database.Drink
import java.lang.NullPointerException

class MixDrinkItemSpinnerAdapter(var drinkList: List<Drink>?, val inflater: LayoutInflater) : BaseAdapter() {
    override fun getCount(): Int = drinkList?.size ?: 0

    override fun getItem(position: Int): Any? {
      return drinkList?.get(position)?: throw NullPointerException()
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_mix_drink, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.tvDrinkMixName.text = drinkList?.get(position)?.drinkName ?: " "

        return view
    }


    private class ItemHolder(row: View?) {
       val tvDrinkMixName: TextView = row?.findViewById(R.id.tvDrinkMixName) as TextView

    }

}