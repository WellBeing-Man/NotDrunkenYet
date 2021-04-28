package com.ldg.notdrunk.main.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ldg.notdrunk.base.BaseRepositoryViewModel
import com.ldg.notdrunk.base.BaseViewModel
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.database.DrinkHistory
import com.ldg.notdrunk.database.GameHistory
import com.ldg.notdrunk.repository.DrinkRepository
import com.ldg.notdrunk.repository.HistoryRepository

class HistoryViewModel(repository: HistoryRepository) : BaseRepositoryViewModel<DrinkDatabase,HistoryRepository>(
    repository
) {
    val historyList: LiveData<List<DrinkHistory>> = repository.drinkHistory

    val gameHistory:LiveData<List<GameHistory>> = repository.gameHistory



}
