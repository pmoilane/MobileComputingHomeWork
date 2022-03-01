package com.example.mobilecomputinghw.ui.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghw.Graph
import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
): ViewModel() {
    /*
    private val _state = MutableStateFlow(Reminder())

    val state: StateFlow<Reminder>
        get() = _state
    */
    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }
    /*
    init {
        viewModelScope.launch {

        }
    }*/
}

data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)

