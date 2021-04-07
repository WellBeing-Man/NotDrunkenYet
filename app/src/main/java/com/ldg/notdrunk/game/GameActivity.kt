package com.ldg.notdrunk.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ldg.notdrunk.R

class GameActivity : AppCompatActivity() {
    companion object {
        //request code
        val CODE_GAME_DONE=1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

    }
}