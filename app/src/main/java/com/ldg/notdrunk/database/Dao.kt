package com.ldg.notdrunk.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ldg.notdrunk.base.BaseDao

@Dao
interface CupDao:BaseDao<Cup> {

    @Query("SELECT * FROM cup")
    fun getAllCupList():LiveData<List<Cup>>
}

@Dao
interface DrinkDao :BaseDao<Drink>{

    @Query("SELECT * FROM drink ORDER BY id ASC")
    fun getAllDrinkList():LiveData<List<Drink>>

}

@Dao
interface DrinkHistoryDao:BaseDao<DrinkHistory> {
    @Query("SELECT * FROM drink_history ORDER BY date_time ASC ")
    fun getHistoryList():LiveData<List<DrinkHistory>>
}


@Dao
interface GameHistoryDao:BaseDao<GameHistory>{
    @Query("SELECT * FROM game_history where day is :today  ")
    fun getGamesOfDay(today:String):LiveData<List<GameHistory>>

    @Query("SELECT * FROM game_history where day is :today order by start_time asc limit 1 ")
    suspend fun getFirstGamesOfDay(today:String):GameHistory

    @Query("SELECT * FROM game_history where day is :today order by start_time desc limit 1 ")
    suspend fun getLastGamesOfDay(today:String):GameHistory
}