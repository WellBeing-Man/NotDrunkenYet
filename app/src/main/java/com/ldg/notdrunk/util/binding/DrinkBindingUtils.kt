package com.ldg.notdrunk.util.binding

import android.content.Context
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ldg.notdrunk.database.Cup
import com.ldg.notdrunk.util.convertCupNumberWithUnit

import java.lang.NullPointerException

@BindingAdapter("currentCup")
fun TextView.currentCups(cupName: String){
    text= cupName
}

@BindingAdapter("cupNumber")
fun TextView.cupNumber(number: Int){
    text= convertCupNumberWithUnit(number, context.resources)
}
