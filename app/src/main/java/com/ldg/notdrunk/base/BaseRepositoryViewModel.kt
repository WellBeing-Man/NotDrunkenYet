package com.ldg.notdrunk.base

import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase

open class BaseRepositoryViewModel<D:RoomDatabase,R:BaseRepository<D>>(val repository: R): ViewModel() {

}