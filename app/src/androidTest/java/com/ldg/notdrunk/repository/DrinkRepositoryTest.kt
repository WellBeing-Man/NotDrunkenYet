package com.ldg.notdrunk.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.database.DrinkDao
import com.ldg.notdrunk.database.DrinkDatabase
import com.ldg.notdrunk.main.MainActivity
import com.ldg.notdrunk.main.drink.DrinkFragment
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class DrinkRepositoryTest {
    private lateinit var db:DrinkDatabase
    private lateinit var drinkDao:DrinkDao

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }


    @Before
    fun createDB(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db= DrinkDatabase.getDatabase(context)
        drinkDao=db.drinkDao();


    }


    @Test
    fun drinkDaoTest(){
        val list = drinkDao.getAllDrinkList()
                MainScope().launch {
                    assertEquals(list.getOrAwaitValue(10).size, 2)
                }
    }
}