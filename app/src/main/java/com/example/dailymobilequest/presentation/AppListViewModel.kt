package com.example.dailymobilequest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailymobilequest.domain.repository.AppListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AppListScreen 관련 뷰모델
 */
@HiltViewModel
class AppListViewModel @Inject constructor(
    private val appListRepository: AppListRepository,
) : ViewModel() {
    private val _uiModel: MutableStateFlow<AppListScreenUiModel> =
        MutableStateFlow(AppListScreenUiModel.Loading)
    val uiModel: StateFlow<AppListScreenUiModel> = _uiModel.asStateFlow()

    init {
        viewModelScope.launch {
            val appList = appListRepository.fetchAppList()
            _uiModel.value = AppListScreenUiModel.Loaded(appList)
        }
    }
}