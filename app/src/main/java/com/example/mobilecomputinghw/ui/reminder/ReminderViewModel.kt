package com.example.mobilecomputinghw.ui.reminder

import androidx.lifecycle.ViewModel
import com.example.mobilecomputinghw.Graph
import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.data.repository.ReminderRepository

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {

    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }
    suspend fun editReminder(reminder: Reminder) {
        return reminderRepository.updateReminder(reminder)
    }
    suspend fun removeReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }

}