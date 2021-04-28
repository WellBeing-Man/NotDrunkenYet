package com.ldg.notdrunk.main.history

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import com.ldg.notdrunk.base.chart.chart.BaseChart
import java.lang.NullPointerException


class HistoryLineChart@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) : BaseChart<DrinkHistoryChartData>(context,attrs,defStyleAttr) {
    private val TAG: String ="HistoryLineChart"
    override var chartData: DrinkHistoryChartData? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG,"HistoryLineChart onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG,"HistoryLineChart onLayout")
    }

    init {
        Log.d(TAG,"HistoryLineChart Init")
    }

    override fun drawChart(canvas: Canvas?) {

        //todo chart draw
    }

}