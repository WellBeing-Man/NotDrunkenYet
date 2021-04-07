package com.ldg.notdrunk.base.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
abstract class BaseDrink() {
    abstract val id:Int
    abstract val drinkName:String
    abstract val alcoholPer:Float?
    abstract val description:String?
    abstract val imageLink:String?
}