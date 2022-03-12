package com.example.mobilecomputinghw.ui.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.mobilecomputinghw.R
import com.example.mobilecomputinghw.Graph
import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.data.repository.ReminderRepository
import com.example.mobilecomputinghw.util.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

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