package com.ldg.notdrunk.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.game.MoleGameViewModel
import com.ldg.notdrunk.intro.IntroViewModel
import com.ldg.notdrunk.main.drink.DrinkViewModel
import com.ldg.notdrunk.main.history.HistoryViewModel
import com.ldg.notdrunk.repository.DrinkRepository
import com.ldg.notdrunk.repository.GameRepository
import com.ldg.notdrunk.repository.HistoryRepository
import java.lang.IllegalArgumentException


//factory class for view model
class ViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(IntroViewModel::class.java)->
                IntroViewModel() as T

            modelClass.isAssignableFrom(DrinkViewModel::class.java)->
                DrinkViewModel(DrinkRepository(DrinkDatabase.getDatabase(context = application.applicationContext))) as T

            modelClass.isAssignableFrom(HistoryViewModel::class.java)->
                HistoryViewModel(HistoryRepository(DrinkDatabase.getDatabase(context = application.applicationContext))) as T

            modelClass.isAssignableFrom(MoleGameViewModel::class.java)->
                MoleGameViewModel(GameRepository(DrinkDatabase.getDatabase(context = application.applicationContext))) as T

            else ->
                throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
