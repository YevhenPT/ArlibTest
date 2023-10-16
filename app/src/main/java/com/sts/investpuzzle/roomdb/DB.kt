package com.sts.investpuzzle.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext


@Database(entities = [RegisterEntity::class], version = 1, exportSchema = false)
abstract class RegisterDatabase: RoomDatabase(){

    abstract fun localDao():DBDao

    companion object {
        private var INSTANCE: RegisterDatabase? = null
        fun getInstance(@ApplicationContext context: Context): RegisterDatabase? {
            if (INSTANCE == null){
                synchronized(RegisterDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RegisterDatabase::class.java, "treadup_db").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}