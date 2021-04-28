package com.ldg.notdrunk.base.chart.data

abstract class ChartData<C:ChartDataContent<out Any,out Any>, D>(
    private val data: List<D>,
    var xLabel:String,
    var yLabel:String){

    private val chartData:ChartDataContent<out Any,out Any>

    init {
            chartData=convertToChartData(data)
    }


    abstract fun convertToChartData(data:List<D>):C
}