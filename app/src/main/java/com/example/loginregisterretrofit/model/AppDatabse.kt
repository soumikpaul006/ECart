package com.example.loginregisterretrofit.model

import MIGRATION_1_2
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loginregisterretrofit.model.dao.ProductDao
import com.example.loginregisterretrofit.model.datalayer.Product

@Database(entities = [Product::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{

        private var INSTANCE:AppDatabase?=null

        fun getInstance(context: Context):AppDatabase
        {
            if(INSTANCE==null)
            {
              INSTANCE= Room.databaseBuilder(context,AppDatabase::class.java,"productDao")
                  .allowMainThreadQueries()
                  .addMigrations(MIGRATION_1_2)
                  .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}