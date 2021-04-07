package com.ldg.notdrunk.main.history

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.charts.LineChart
import com.ldg.notdrunk.databinding.HistoryLineChartBinding


class HistoryLineChart: View {
    constructor(context: Context)
            : this(context, null)
    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)


}
