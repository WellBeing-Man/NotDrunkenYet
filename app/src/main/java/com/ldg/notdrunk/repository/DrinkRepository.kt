package com.ldg.notdrunk.repository

import com.ldg.notdrunk.base.BaseRepository
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.database.DrinkHistory
import com.ldg.notdrunk.database.GameHistory
import com.ldg.notdrunk.util.TimeFormat
import com.ldg.notdrunk.util.currentFormatted
import com.ldg.notdrunk.util.drinkLevelMeasure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.NullPointerException

class DrinkRepository (
    override val database: DrinkDatabase): BaseRepository<DrinkDatabase>(database = database) {


    val drinks=database.drinkDao().getAllDrinkList()
    val cups=database.cupDao().getAllCupList()

    suspend fun insertHistory(drinkHistory: DrinkHistory?) {
        withContext(Dispatchers.IO){

            drinkHistory?: return@withContext

            database.historyDay().insert(drinkHistory!!)
        }
    }

    suspend fun insertDrink(newDrink: Drink) {
        withContext(Dispatchers.IO){
            database.drinkDao().insert(newDrink)
        }
    }

    suspend fun getDrinkLevel(): String {
        var difference=0;
        var firstGame:GameHistory? = null
        var lastGame:GameHistory? = null
        withContext(Dispatchers.IO){
            firstGame=database.gameHistoryDao().getFirstGamesOfDay(currentFormatted(format = TimeFormat.TODAY.format))
            lastGame=database.gameHistoryDao().getFirstGamesOfDay(currentFormatted(format = TimeFormat.TODAY.format))
        }

        return try {
            drinkLevelMeasure(firstGame!!.accuracy, lastGame!!.accuracy).name
        }catch (n:NullPointerException){
            n.printStackTrace()
            ""
        }
    }


}