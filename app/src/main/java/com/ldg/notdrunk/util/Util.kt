package com.ldg.notdrunk.util

import android.content.res.Resources
import android.widget.EditText
import com.ldg.notdrunk.R
import com.ldg.notdrunk.game.DrinkLevel
import java.text.SimpleDateFormat
import java.util.*

fun convertCupNumberWithUnit(number: Int, resources: Resources):String{
    val unit = resources.getString(R.string.drink_unit_kor)
    return "${number}${unit}"
}


fun convertRatioToNature(ratio:Int):String{
    var ratioOther=10-ratio;

    return "$ratio : $ratioOther"
}

fun EditText.clear(){
    text=null
}


fun currentFormatted(format:String):String{
    return SimpleDateFormat(format).format(
        Date(System.currentTimeMillis())
    )
}

fun formattingInputTime(currentTime:Long,format:String):String{
    return SimpleDateFormat(format).format(currentTime)
}

fun drinkLevelMeasure(firstAccuracy:Float, lastAccuracy:Float):DrinkLevel{
    val difference=firstAccuracy-lastAccuracy
    return if(difference<=5){
        DrinkLevel.FINE
    }else if(difference<=10){
        DrinkLevel.LITTLE_BIT
    }else if(difference<=15) {
        DrinkLevel.MODERATELY
    }else
        DrinkLevel.WASTED
}

enum class TimeFormat(val format: String){
    TODAY("yyyy-MM-dd"),
    HOUR("yyyy-MM-dd hh"),
    MINUTE("yyyy-MM-dd hh:mm"),
    SECOND("yyyy-MM-dd hh:mm:ss")
}