package com.example.composecampgroup4.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composecampgroup4.data.local.model.JarDbModel

@Database(entities = [JarDbModel::class], version = 2, exportSchema = false)
abstract class JarDatabase : RoomDatabase() {

    abstract fun getJarDao(): JarDao
}