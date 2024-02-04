package com.billion_dollor_company.notesapp.ui.screen.toBuy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.billion_dollor_company.notesapp.model.ToBuyInfo
import com.billion_dollor_company.notesapp.repository.ToBuyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ToBuyViewModel @Inject constructor(
    private val toBuyRepository: ToBuyRepository
) : ViewModel() {
    private val toBuyStateFlow = MutableStateFlow<List<ToBuyInfo>>(emptyList())
    val toBuyInfoList = toBuyStateFlow.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            toBuyRepository.getAllBuyItems().distinctUntilChanged()
                .collect { listOfItems ->
                    toBuyStateFlow.value = listOfItems
                }

        }
    }

    fun addToBuyItem(toBuyInfo: ToBuyInfo) = viewModelScope.launch {
        toBuyRepository.addItem(toBuyInfo)
    }

    fun setToBuyStatus(toBuyInfo: ToBuyInfo) = viewModelScope.launch {
        toBuyInfo.status = !toBuyInfo.status
        toBuyRepository.addItem(toBuyInfo)
    }

    fun deleteToBuyItem(toBuyInfo: ToBuyInfo) = viewModelScope.launch {
        toBuyRepository.deleteItem(toBuyInfo)
    }
}