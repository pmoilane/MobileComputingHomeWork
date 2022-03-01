package com.example.mobilecomputinghw.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilecomputinghw.data.entity.Reminder

@Database(
    entities = [Reminder::class],
    version = 1,
    exportSchema = false
)

abstract class HWDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}