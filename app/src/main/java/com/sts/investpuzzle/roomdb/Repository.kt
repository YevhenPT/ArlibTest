package com.sts.investpuzzle.roomdb

import android.util.Log

class Repository (private val dao: DBDao) {

    val users = dao.getAllUsers()
     suspend fun insert(user: RegisterEntity) {

         Log.d("insert=====","db")
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String):RegisterEntity?{
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return dao.getUserName(userName)
    }
    //suspend fun deleteAll(): Int {
    //    return dao.deleteAll()
    //}

}