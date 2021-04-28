package com.ldg.notdrunk.main.history

import com.ldg.notdrunk.base.chart.data.ChartData
import com.ldg.notdrunk.base.chart.data.ChartDataContent
import com.ldg.notdrunk.database.DrinkHistory



class DrinkHistoryChartData(data: List<DrinkHistory>, xLabel: String, yLabel: String) :
    ChartData<HistoryDataContent,DrinkHistory>(data, xLabel, yLabel) {


    override fun convertToChartData(data: List<DrinkHistory>): HistoryDataContent {
        var list= mutableListOf<Pair<String,Float>>()

        data.map {
            list.add(Pair<String,Float>(it.dateTime,it.alcoholMass))
        }

        return HistoryDataContent(list)
    }
}


class HistoryDataContent(content: List<Pair<String, Float>>) : ChartDataContent<String, Float>(content)