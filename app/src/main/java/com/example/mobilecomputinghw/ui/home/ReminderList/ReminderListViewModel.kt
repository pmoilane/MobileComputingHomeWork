package com.example.mobilecomputinghw.ui.home.ReminderList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputinghw.Graph
import com.example.mobilecomputinghw.data.entity.Reminder
import com.example.mobilecomputinghw.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryReminderViewState())

    val state: StateFlow<CategoryReminderViewState>
        get() = _state

    private val _state2 = MutableStateFlow(CategoryPastReminderViewState())

    val state2: StateFlow<CategoryPastReminderViewState>
        get() = _state2

    init {

        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = CategoryReminderViewState(
                    reminders = list
                )
            }
        }
        viewModelScope.launch {
            reminderRepository.pastReminders(currentTime = System.currentTimeMillis())
                .collect { list2 ->
                    _state2.value = CategoryPastReminderViewState(
                        pastReminders = list2
                    )
                }
        }
    }
}



data class CategoryReminderViewState(
    val reminders: List<Reminder> = emptyList()
)

data class CategoryPastReminderViewState(
    val pastReminders: List<Reminder> = emptyList()
)