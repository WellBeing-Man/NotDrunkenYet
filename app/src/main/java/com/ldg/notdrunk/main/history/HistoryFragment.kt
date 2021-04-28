package com.ldg.notdrunk.main.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.MVVMFragment

import com.ldg.notdrunk.databinding.FragmentHistoryBinding

class HistoryFragment : MVVMFragment<HistoryViewModel,FragmentHistoryBinding>() {

    private val DEBUG_TAG="HistoryFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectViewModel(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       bindingView(inflater = inflater, container = container, redId = R.layout.fragment_history)
        viewBinding.historyViewModel=viewModel

        //draw graph
        viewModel.historyList.observe(this.viewLifecycleOwner, Observer { list->
            viewBinding.lineChart.chartData= DrinkHistoryChartData(list,"마신 시간","마신양")
        }
        )

        viewModel.gameHistory.observe(this.viewLifecycleOwner, Observer { list->
            list.forEach { history->
                Log.d(DEBUG_TAG+"game",history.accuracy.toString()+" "+history.dateTime)
            }
        })


        return viewBinding.root
    }
}