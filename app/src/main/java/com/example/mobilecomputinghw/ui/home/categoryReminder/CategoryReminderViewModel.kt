package com.example.mobilecomputinghw.ui.home.categoryReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghw.Graph
import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class CategoryReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    init {

        viewModelScope.launch {
            reminderRepository.pastReminders(currentTime = System.currentTimeMillis()).collect { list ->
                _state.value = CategoryReminderViewState(
                    reminders = list
                )
            }
        }
    }
}

data class CategoryReminderViewState(
    val reminders: List<Reminder> = emptyList()
)