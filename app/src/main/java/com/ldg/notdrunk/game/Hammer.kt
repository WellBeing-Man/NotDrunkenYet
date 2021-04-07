package com.ldg.notdrunk.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.GameItemView
import java.lang.Math.abs

@SuppressLint("AppCompatCustomView")
class Hammer@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) :
  GameItemView(context,attrs,defStyleAttr) {


    private val TAG="Hammer Touch event"
    private var oldXValue=0f
    private var oldYValue=0f

    var isHit: Boolean = false

    lateinit var target: Target

    init {
        this.setImageResource(R.drawable.ic_baseline_flash_on_24)
        minimumWidth=300
        minimumHeight=300
        endY=0.7f
        startY=0.3f
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        selectStartPosition()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val action= event?.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                oldXValue=event.x
                oldYValue=event.y
                Log.d(TAG, "Action was DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "Action was MOVE")
                x = x + event.x - width / 2
                y = y + event.y - height / 2

            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "Action was UP")
                if(x<0)
                    x=0f
                else if(x+width > (parent as ViewGroup).width ){
                    x= (parentWidth - width).toFloat()
                }
                if(y<0)
                    y=0f
                else if(y+height > (parent as ViewGroup).height ){
                    y= (parentHeight - height).toFloat()
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "Action was CANCEL")
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(TAG, "Movement occurred outside bounds of current screen element")
            }
            else -> super.onTouchEvent(event)
        }

        checkIfHitTarget()

        return true
    }



    private fun checkIfHitTarget() {
        if (this::target.isInitialized) {
            if (abs(target.x - x) <= width / 2 && abs(target.y - y) <= height / 2) {
                isHit = true
            }
        }
    }
}