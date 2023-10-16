package com.sts.investpuzzle.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBDao{
    @Insert
     fun insert(register:RegisterEntity)

    @Query("SELECT *FROM register_db ORDER bY userId DESC")
    fun getAllUsers(): List<RegisterEntity>

    @Query("SELECT *FROM register_db WHERE user_name LIKE :userName")
     fun getUserName(userName: String):RegisterEntity

}