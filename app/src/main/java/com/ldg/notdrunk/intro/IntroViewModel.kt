package com.ldg.notdrunk.intro


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ldg.notdrunk.base.BaseRepositoryViewModel
import com.ldg.notdrunk.base.BaseViewModel
import com.ldg.notdrunk.database.DrinkDatabase
import kotlinx.coroutines.*

class IntroViewModel : BaseViewModel() {

    private val _onLogoAnimation = MutableLiveData<Boolean>()
    val onLogoAnimation : LiveData<Boolean>
        get() =_onLogoAnimation

    private val wait= CoroutineScope(Dispatchers.Default).launch {
        var i = 0
        while (i < 5) {
            delay(300)
            i++
            Log.d("logoWait", "" + i)
        }
    }

    init {
        _onLogoAnimation.value=true;
    }



    private fun onAnimationStop(){
        _onLogoAnimation.value=false;
    }


    suspend fun startSettingApp(context:Context) {
        connectDB(context)
        wait.join()
        onAnimationStop()
    }

    private suspend fun connectDB(context: Context) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
               DrinkDatabase.getDatabase(context)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        wait.cancel()
    }
}