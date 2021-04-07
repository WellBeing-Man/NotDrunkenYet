package com.ldg.notdrunk.main.drink


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ldg.notdrunk.base.BaseRepositoryViewModel
import com.ldg.notdrunk.base.entity.BaseDrink
import com.ldg.notdrunk.database.*
import com.ldg.notdrunk.main.drink.callback.ChangeCupCallBack
import com.ldg.notdrunk.main.drink.callback.ChangeDrinkCallBack
import com.ldg.notdrunk.repository.DrinkRepository
import com.ldg.notdrunk.util.TimeFormat
import com.ldg.notdrunk.util.currentFormatted
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.NullPointerException
import java.util.*

class DrinkViewModel(repository: DrinkRepository) : BaseRepositoryViewModel<DrinkDatabase,DrinkRepository>(repository) {

    /**
     * Constant values
     * */
    private val DEBUG_TAG="DrinkViewModel debug"
    /**
     * list of data
     */
    val drinkList : LiveData<List<Drink>> = repository.drinks
    val cupList : LiveData<List<Cup>> = repository.cups
    /**
     *  UI events data
     **/
    private val _cupNumber=MutableLiveData<Int>().apply { value=1 }

    val cupNumber:LiveData<Int>
        get() = _cupNumber

    private val _drinkIndex=MutableLiveData<Int>().apply {value= 0 }

    val drinkIndex:LiveData<Int>
        get() = _drinkIndex

    private val _cupIndex=MutableLiveData<Int>().apply { value=0 }

    val cupIndex:LiveData<Int>
        get() = _cupIndex


    private val _onClickSelectDrink = MutableLiveData<Boolean>()
    val onClickSelectDrink:LiveData<Boolean>
        get() = _onClickSelectDrink

    private val _onClickDrinkButton=MutableLiveData<Boolean>()
    val onClickDrinkButton: LiveData<Boolean>
        get() = _onClickDrinkButton


    // mix button event watch
    private val _onMixDrink= MutableLiveData<Boolean>().apply { value=false }
    val onMixDrink:LiveData<Boolean>
        get() = _onMixDrink


    private val _rightMixDrinkIndex= MutableLiveData<Int>().apply {value= 0  }
    val rightMixDrinkIndex:LiveData<Int>
        get() = _rightMixDrinkIndex

    private val _leftMixDrinkIndex= MutableLiveData<Int>().apply {  value=1 }
    val leftMixDrinkIndex:LiveData<Int>
        get() = _leftMixDrinkIndex


    private val _dbErrorOccurs= MutableLiveData<Boolean>()
    val dbErrorOccurs:LiveData<Boolean>
        get() = _dbErrorOccurs
    /**
     * call backs
     * */
    val changeCupCallBack= object : ChangeCupCallBack {
        override fun changeCup(index: Int) {
            setCurrentCupIndex(index)
        }
    }
    val changeDrinkCallBack= object : ChangeDrinkCallBack {
        override fun changeDrink(index: Int) {
            setCurrentDrinkIndex(index)
        }

    }

    /**
     * Coroutine jobs
     * */
    init {
        loadDataFromDB()
    }

    /**
     * click events
     * */
    fun onDrinkMore(){
        _cupNumber.value=_cupNumber.value?.plus(1)
    }

    fun onDrinkLess(){
        if(_cupNumber.value!! >0)
        _cupNumber.value=_cupNumber.value?.minus(1)
        }

    fun onNextDrink(){
        if(_drinkIndex?.value!! < drinkList.value!!.size -1 )
            _drinkIndex.value= _drinkIndex.value!!.plus(1)
    }

    fun onLastDrink(){
        if(_drinkIndex.value!! >0 )
            _drinkIndex.value= _drinkIndex.value!!.minus(1)
    }

    fun onClickSelect(){
        _onClickSelectDrink.value=true
    }

    fun onClickSelectFinish(){
        _onClickSelectDrink.value=false
    }

    fun onClickDrinkButton(){
        _onClickDrinkButton.value=true
    }

    fun onClickDrinkButtonFinish(){
        _onClickDrinkButton.value=false
    }

    fun onMixDrink(){
        _onMixDrink.value = !_onMixDrink.value!!
    }


    /***
    * UI events
    * */
    private fun onDataBaseLoadingError(){
        //todo error ui event
        _dbErrorOccurs.value=true
    }

    fun onDataBaseLoadingErrorDone(){
        _dbErrorOccurs.value=false
    }

    /**
     * index setter
     * */
    private fun setCurrentDrinkIndex(index:Int){
        _drinkIndex.value=index
    }

    private fun setCurrentCupIndex(index:Int){
        _cupIndex.value=index
    }

    fun setRightMixDrinkIndex(index:Int){
        _rightMixDrinkIndex.value=index
    }
    fun setLeftMixDrinkIndex(index:Int){
        _leftMixDrinkIndex.value=index
    }


    /**
     *  data loading from db
     * */

    private fun loadDataFromDB(){
        viewModelScope.launch {
            try {
                //todo add data refresh
               // repository.refreshDrinkList()

            }catch (e:IOException){
                //todo show db error message
                onDataBaseLoadingError()
                e.printStackTrace()
            }
        }
    }



    /**
     *  data writing
     * */
    fun saveHistory() {
        viewModelScope.launch {
            val makeHistoryAsync:Deferred<DrinkHistory?> =viewModelScope.async {
                
                val history:DrinkHistory?=try {
                val drink = drinkList.value!![drinkIndex.value!!]
                val cup = cupList.value!![cupIndex.value!!]
                val curTime =
                    currentFormatted(TimeFormat.SECOND.format)
                val drinkLevel=repository.getDrinkLevel()
                DrinkHistory(
                    curTime,
                    drink.drinkName,
                    cup.volume * drink.alcoholPer!! *  _cupNumber.value!!,
                    drinkLevel
                ) }catch (n:NullPointerException){
                    n.printStackTrace()
                    null
                }
                history
            }
            repository.insertHistory(makeHistoryAsync.await())
            _cupNumber.value=1
        }
    }

    private suspend fun makeCurrentHistory(): DrinkHistory? {

        return if(drinkList.value!!.size < 0) {
            null
        }else{
            val drink = drinkList.value!![drinkIndex.value!!]
            val cup = cupList.value!![cupIndex.value!!]
            val curTime =
                currentFormatted(TimeFormat.SECOND.format)
            val drinkLevel=repository.getDrinkLevel()
            DrinkHistory(
                curTime,
                drink.drinkName,
                cup.volume * drink.alcoholPer!! *  _cupNumber.value!!,
                drinkLevel
            )

        }
    }

    private fun insertNewDrink(newDrink:Drink){
        Log.d(DEBUG_TAG,newDrink.description.toString())
            viewModelScope.launch {
                repository.insertDrink(newDrink)
            }

    }

    fun addNewMixedDrink(name: String, ratioA: String, ratioB: String) {
            var newDrink:BaseDrink
            val drinkA:Drink=drinkList.value?.get(_rightMixDrinkIndex.value!!)!!
            val drinkB:Drink=drinkList.value?.get(_leftMixDrinkIndex.value!!)!!
            val ratio=ratioA.toFloat()/(ratioA.toFloat()+ratioB.toFloat()) *10
            newDrink=MixedDrink(drinkA,drinkB,name,ratio.toInt())
            insertNewDrink(newDrink = newDrink.toDrinkEntity())
    }


}