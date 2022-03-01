package com.example.mobilecomputinghw.ui.home.categoryReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghw.data.entity.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class CategoryReminderViewModel : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    init {
        val list = mutableListOf<Reminder>()
        for (x in 1..20) {
            list.add(
                Reminder(
                    reminderId = x.toLong(),
                    reminderTitle = "$x reminder",
                    reminderCategory = "Alarm",
                    reminderDate = Date()
                )
            )
        }

        viewModelScope.launch {
            _state.value = CategoryReminderViewState(
                reminders = list
            )
        }
    }
}

data class CategoryReminderViewState(
    val reminders: List<Reminder> = emptyList()
)