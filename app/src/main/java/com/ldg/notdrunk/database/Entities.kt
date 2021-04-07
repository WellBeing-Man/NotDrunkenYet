package com.ldg.notdrunk.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ldg.notdrunk.base.entity.BaseDrink
import com.ldg.notdrunk.util.convertRatioToNature

@Entity(tableName = "cup")
class Cup(
    @PrimaryKey
    val id:Int,
    @ColumnInfo(name = "cup_name")
    val cupName:String,
    @ColumnInfo(name = "volume")
    val volume:Float,
    @ColumnInfo(name = "image_link")
    val imageLink:String?)
{
    fun ml(number:Int) = volume*number
}


@Entity(tableName = "drink")
data class Drink(
    @PrimaryKey
    override val id:Int,

    @ColumnInfo(name = "drink_name")
    @NonNull
    override val drinkName:String,

    @ColumnInfo(name = "alcohol_per")
    override val alcoholPer:Float?,
    @ColumnInfo(name = "description")
    override val description:String?,
    @ColumnInfo(name = "image_link")
    override val imageLink:String?): BaseDrink() {

    fun percentString() :String = "${alcoholPer?.div(1f)}%/ml"
}

@Entity(tableName = "drink_history")
data class DrinkHistory(
    @PrimaryKey
    @ColumnInfo(name = "date_time")
    val dateTime:String,
    @ColumnInfo(name = "drink_name")
    val drinkName:String,
    @ColumnInfo(name = "alcohol_mass")
    val alcoholMass:Float,
    @ColumnInfo(name="drunk_degree")
    val drunkDegree:String
)


@Entity(tableName = "game_history")
data class GameHistory(
    @PrimaryKey
    @ColumnInfo(name = "start_time")
    val dateTime:String,
    @ColumnInfo(name = "finished_time")
    val finishedTime:String,
    @ColumnInfo(name = "accuracy")
    val accuracy:Float,
    @ColumnInfo(name = "day")
    val day:String
)

open class MixedDrink(private val drinkA:Drink, private val drinkB:Drink, val name:String, private val ratio:Int): BaseDrink(){


    override val id: Int= drinkA.id+drinkB.id
    override val drinkName: String=name
    override val alcoholPer: Float?= mixedDrinkPer()
    override val description: String?=mixedDescription()
    override val imageLink: String?=""

    private fun mixedDescription(): String? {
        return "${drinkA.drinkName}와 ${drinkB.drinkName}을 ${convertRatioToNature(ratio)}로 섞음"
    }



    private fun mixedDrinkPer(): Float? {
        var a:Float= (drinkA.alcoholPer?.times(ratio) ?: 0F)
        var b:Float=(drinkB.alcoholPer?.times((1-ratio)) ?: 0F)
        return a.plus(b)
    }

    fun toDrinkEntity(): Drink {
        return Drink(id, drinkName, alcoholPer, description, imageLink)
    }

}