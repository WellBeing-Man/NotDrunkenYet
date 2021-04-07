package com.ldg.notdrunk.base

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout

abstract class GameBoard@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) :
    ConstraintLayout(context,attrs,defStyleAttr) {
        abstract fun startGame()
    }
