package com.ldg.notdrunk.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.GameItemView

@SuppressLint("AppCompatCustomView")
class Target@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    GameItemView(context,attrs,defStyleAttr) {

    private val DEBUG_TAG: String="Target"


    private val hitAnimation=AnimationUtils.loadAnimation(context,R.anim.hit_animation)


    init {
        this.setImageResource(R.drawable.ic_baseline_favorite_24)
        setViewDefault(300,300,0.05f,0.25f)
    }


    fun onTargetHit(){
        startAnimation(hitAnimation)
    }


}