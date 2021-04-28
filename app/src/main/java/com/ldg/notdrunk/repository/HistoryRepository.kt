package com.ldg.notdrunk.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.ldg.notdrunk.base.BaseRepository
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.database.DrinkHistory
import com.ldg.notdrunk.database.GameHistory
import com.ldg.notdrunk.util.TimeFormat
import com.ldg.notdrunk.util.currentFormatted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.NullPointerException

class HistoryRepository (
    override val database: DrinkDatabase): BaseRepository<DrinkDatabase>(database = database) {


    val drinkHistory: LiveData<List<DrinkHistory>> =database.historyDay().getHistoryList()
    val gameHistory:LiveData<List<GameHistory>> =database.gameHistoryDao().getAllGames()
}