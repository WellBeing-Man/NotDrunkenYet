package com.ldg.notdrunk.repository

import com.ldg.notdrunk.base.BaseRepository
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.database.DrinkHistory
import com.ldg.notdrunk.database.GameHistory
import com.ldg.notdrunk.util.TimeFormat
import com.ldg.notdrunk.util.currentFormatted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class GameRepository(database: DrinkDatabase) : BaseRepository<DrinkDatabase>(database) {

    val todayGameList=database.gameHistoryDao().getGamesOfDay(currentFormatted(TimeFormat.TODAY.format))
    var todayFirstGame:GameHistory? = null


    suspend fun insertGameHistory(gameHistory: GameHistory?) {
        withContext(Dispatchers.IO){

            gameHistory?: return@withContext

            database.gameHistoryDao().insert(gameHistory)
        }
    }

    suspend fun selectFirstGameOfDay(){
        withContext(Dispatchers.IO){
            todayFirstGame=database.gameHistoryDao().getFirstGamesOfDay(currentFormatted(TimeFormat.TODAY.format))
        }
    }

}