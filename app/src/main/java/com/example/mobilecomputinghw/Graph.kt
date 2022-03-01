package com.example.mobilecomputinghw

import android.content.Context
import androidx.room.Room
import com.example.mobilecomputinghw.data.repository.ReminderRepository
import com.example.mobilecomputinghw.data.room.HWDatabase

object Graph {
    lateinit var database: HWDatabase

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, HWDatabase::class.java, "hwData.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}