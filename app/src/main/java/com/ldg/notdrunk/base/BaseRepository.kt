package com.ldg.notdrunk.base

import android.content.Context
import androidx.room.RoomDatabase

abstract class BaseRepository<D:RoomDatabase>(open val database: D) {

}