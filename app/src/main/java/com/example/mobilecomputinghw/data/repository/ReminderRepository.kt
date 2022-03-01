package com.example.mobilecomputinghw.data.repository

import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()

    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
}