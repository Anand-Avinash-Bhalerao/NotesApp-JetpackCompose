package com.billion_dollor_company.notesapp.ui.screen.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.ChecklistInfo
import com.billion_dollor_company.notesapp.repository.ChecklistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChecklistViewModel @Inject constructor(
    private val checklistRepository: ChecklistRepository
) : ViewModel() {
    private val _checklistStateFlow = MutableStateFlow<List<ChecklistInfo>>(emptyList())
    val checklistList = _checklistStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            checklistRepository.getAllItems().distinctUntilChanged()
                .collect { listOfItems ->
                    _checklistStateFlow.value = listOfItems
                }
        }
    }

    fun addItem(checklistInfo: ChecklistInfo) = viewModelScope.launch {
        checklistRepository.addItem(checklistInfo)
    }

    fun setItemStatus(checklistInfo: ChecklistInfo) = viewModelScope.launch {
        checklistInfo.status = !checklistInfo.status
        checklistRepository.addItem(checklistInfo)
    }

    fun deleteItem(checklistInfo: ChecklistInfo) = viewModelScope.launch {
        checklistRepository.deleteItem(checklistInfo)
    }
}