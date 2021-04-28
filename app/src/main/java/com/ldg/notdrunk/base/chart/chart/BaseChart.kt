package com.ldg.notdrunk.base.chart.chart

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ldg.notdrunk.base.chart.data.ChartData
import com.ldg.notdrunk.base.chart.data.ChartDataContent
import com.ldg.notdrunk.base.chart.interfaces.UseChartData

abstract class BaseChart<D : ChartData<out ChartDataContent<out Any, out Any>,out Any>>@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) :
    View(context,attrs,defStyleAttr),UseChartData<D>{


        var primaryWidth:Int = 1200


        val primaryHeight:Int
            get() = (primaryWidth*0.7f).toInt()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

            drawChart(canvas)

    }

    //need to draw chart here
    abstract fun drawChart(canvas: Canvas?)


}
