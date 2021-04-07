package com.ldg.notdrunk.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ldg.notdrunk.base.BaseRepositoryViewModel
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.database.GameHistory
import com.ldg.notdrunk.repository.GameRepository
import com.ldg.notdrunk.util.TimeFormat
import com.ldg.notdrunk.util.formattingInputTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MoleGameViewModel(repository: GameRepository) : BaseRepositoryViewModel<DrinkDatabase,GameRepository>(
    repository
){


    private val DEBUG_TAG: String="MoleGameViewModel DEBUG"

    /**
     * UI works status
     * */
    private val _onGameFinished=MutableLiveData<Boolean>()
    val onGameFinished:LiveData<Boolean>
    get() = _onGameFinished

    val moleGameCallBack= object :MoleGameCallBack{
        override fun storeGameResult(score: Float, start: Long, end: Long) {
            viewModelScope.launch {
                Log.d(DEBUG_TAG,"$score $start $end")
                onWriteGameHistory(score,start,end)
            }

        }

    }

    suspend fun onWriteGameHistory(score: Float, start: Long, end: Long){

        val formattedStartTime=formattingInputTime(start, TimeFormat.SECOND.format)
        val formattedEndTime=formattingInputTime(end, TimeFormat.SECOND.format)
        val formattedDay=formattingInputTime(start,TimeFormat.MINUTE.format)
        val history=GameHistory(formattedStartTime,formattedEndTime,score,formattedDay)
        withContext(Dispatchers.IO) {
            repository.insertGameHistory(history)
        }
        onFinishGame()
    }


    fun onFinishGame(){
        _onGameFinished.value=true
    }

    fun onFinishGameDone(){
        _onGameFinished.value=false
    }
}
