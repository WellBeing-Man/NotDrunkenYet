package com.ldg.notdrunk.base.chart.interfaces

import com.ldg.notdrunk.base.chart.data.ChartData
import com.ldg.notdrunk.base.chart.data.ChartDataContent

interface UseChartData<C:ChartData<out ChartDataContent<out Any,out Any>,out Any>> {
    var chartData:C?


}