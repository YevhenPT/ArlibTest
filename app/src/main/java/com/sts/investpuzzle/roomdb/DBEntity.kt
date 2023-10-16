package com.sts.investpuzzle.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "register_db")
data class RegisterEntity(
    @PrimaryKey(autoGenerate = true)
    var userId:Int = 0,

    @ColumnInfo(name = "user_name")
    var userName:String,

    @ColumnInfo(name = "email")
    var email:String
)