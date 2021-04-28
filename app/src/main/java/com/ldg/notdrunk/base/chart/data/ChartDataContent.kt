package com.ldg.notdrunk.base.chart.data

abstract class ChartDataContent<X ,Y>(private val content: List<Pair<X, Y>>){

    private lateinit var xList:MutableList<X>
    private lateinit var yList:MutableList<Y>

    fun getXData():List<X>{
        if(this::xList.isInitialized){
            return xList
        }
            content.forEach { it ->
                xList.add(it.first)
            }
        return xList
    }

    fun getYData():List<Y>{
        if(this::yList.isInitialized){
            return yList
        }
        content.forEach { it ->
            yList.add(it.second)
        }
        return yList
    }
}
