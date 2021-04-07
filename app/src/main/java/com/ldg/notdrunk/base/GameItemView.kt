package com.ldg.notdrunk.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import com.ldg.notdrunk.R
import java.lang.Exception
import kotlin.math.abs
import kotlin.random.Random

@SuppressLint("AppCompatCustomView")
abstract class GameItemView@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0) :
    ImageView(context,attrs,defStyleAttr){

    //parent layout's width, height for move event
     var parentWidth=0
     var parentHeight=0

    //limit start y position
      var startY=0f
            set(value) {
                if(value>=1){
                    throw Exception("need value less than 1")
                }else{
                    field = value
                }
            }

    //limit end y position
      var endY= 0f
          set(value) {
              if(value>=1){
                  throw Exception("need value more than 1")
              }else{
                  field = value
              }
          }



     override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
         super.onLayout(changed, left, top, right, bottom)
         //get parent value
         parentWidth=(parent as ViewGroup).width
         parentHeight=(parent as ViewGroup).height
     }


    //assign start position random
    fun selectStartPosition(){
        val random = Random
        var next = abs(random.nextInt()) % endY * 100 + 1
        this.x = parentWidth * 0.2f + parentWidth % (parentWidth * next / 100)
        this.y = parentHeight * startY + parentHeight % (parentHeight * next / 100)
    }


    //set with, height, start position limit
    fun setViewDefault(minWith: Int, minHeight: Int, sY: Float, eY: Float) {
        minimumWidth = minWith
        minimumHeight = minHeight
        startY = sY
        endY = eY
    }



}