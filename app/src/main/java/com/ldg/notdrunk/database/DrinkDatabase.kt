package com.ldg.notdrunk.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Drink::class, Cup::class, DrinkHistory::class,GameHistory::class],version = 2, exportSchema = false)
abstract class DrinkDatabase : RoomDatabase() {
    abstract fun drinkDao():DrinkDao
    abstract fun cupDao():CupDao
    abstract fun historyDay():DrinkHistoryDao
    abstract fun gameHistoryDao():GameHistoryDao


    companion object{
        @Volatile
        private  var dbInstacne: DrinkDatabase?=null

        fun getDatabase(context: Context): DrinkDatabase {

            synchronized(this) {
                var instance= dbInstacne

                if (instance==null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        DrinkDatabase::class.java,
                        "drink_db")
                        .createFromAsset("primalDB.db")
                        .build()
                    dbInstacne=instance
                }
                return instance
            }
        }
    }

}


