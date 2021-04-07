package com.ldg.notdrunk.game

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import com.ldg.notdrunk.base.GameBoard
import kotlinx.coroutines.*
import java.util.*
import com.ldg.notdrunk.game.Target as Target
import kotlin.random.Random as Random

class MoleGameBoard @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) :
    GameBoard(context,attrs,defStyleAttr) {

    /**
     * DEBUG_TAG
     * */
    private val DEBUG_TAG: String="MoleGameBoard"

    /**
     * Final values
     * **/
    private val targetCount=20f
    private val timeLimits=20

    /**
     * Call Back from viewModel
     *
     * */
    lateinit var moleGameCallBack:MoleGameCallBack

    private val hammer: Hammer by lazy {
            Hammer(context)
        }

    private val targets= Stack<Target>()

    /**
     * game history data
     * */
    private var hitCount=0f
    private var startTime:Long =0
    private var endTime:Long =0

    /**
     * target position list
     * */
    private val targetPositions= mutableListOf<Pair<Float,Float>>()


    /**
     * coroutine job for gaming
     * 1.
     * */
    private val moleGameJob:Job by lazy {
        CoroutineScope(Dispatchers.Default).launch{
            val wait= MainScope().launch {
                waitMessage()
            }
            wait.join()
            startTime=System.currentTimeMillis()
            targets.forEach { target ->

                //add next target on view
                val addView = MainScope().launch {
                    onNewTarget(target)
                }
                    addView.join()


                    if (waitHit()) {
                        MainScope().launch {
                        target.onTargetHit()
                            hitCount++
                        }
                    }

                    MainScope().launch {
                            removeTarget()
                    }

            }
            endTime=System.currentTimeMillis()
            moleGameCallBack.storeGameResult(hitCount/targetCount,startTime,endTime)
        }
    }

    //wait user ready
    private suspend fun waitMessage(){
        Toast.makeText(context,"Ready...",Toast.LENGTH_SHORT).show()
        delay(2000)
        Toast.makeText(context,"Go!",Toast.LENGTH_SHORT).show()
        delay(1000)
    }


    /**
     * 1. assign target on hammer
     * 2. add target on view
     * */
    private fun onNewTarget(target: Target) {
        hammer.target = target
        addView(target)
    }

    //remove current target view
    private fun removeTarget() {
        removeView(hammer.target)
        hammer.isHit = false
    }

    //wait until hammer touch to target
    private suspend fun waitHit(): Boolean {
        var count=0
        while (!hammer.isHit && count<timeLimits) {
            delay(100)
            count++;
        }
        return count<timeLimits
    }

    //scope for mole game
    private val moleGameScope= CoroutineScope(Dispatchers.Default+moleGameJob)




    init {
        this.addView(hammer)
    }

    //after View size assigned, create and add targets
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        createTargetPosition()
        addTargetViews()
        startGame()
    }

    //create target position random
    private fun createTargetPosition() {
        repeat(targetCount.toInt()){
            val x =width*0.1f + (Random.nextFloat()*10000)%(width*0.8f)
            val y= height*0.1f+ (Random.nextFloat()*10000)%(height*0.8f)
            targetPositions.add(Pair(x,y))
        }
    }

    //create view on random position add to list
    private fun addTargetViews() {
        targetPositions.forEach { it->
            val target=Target(context).apply {
                x=it.first
                y=it.second
            }
            targets.add(target)
        }
    }

    //start game
    override fun startGame() {
        moleGameScope.launch {
            moleGameJob.start()
            moleGameJob.join()

        }
    }


    //destroy coroutine
    fun destroyJob(){
        moleGameScope.cancel("EndView")
    }



}


interface MoleGameCallBack{
    /**
     * call back for store game history
     * */
    fun storeGameResult(score:Float, start:Long, end:Long)
}