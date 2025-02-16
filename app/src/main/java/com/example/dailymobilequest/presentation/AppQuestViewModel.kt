package com.example.dailymobilequest.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dailymobilequest.domain.repository.AppListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 하나의 앱 퀘스트 모음 관련 뷰모델
 */
@HiltViewModel
class AppQuestViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    appListRepository: AppListRepository
) : ViewModel() {
    private val packageName: String = savedStateHandle["packageName"] ?: ""

    private val _uiModel: MutableStateFlow<QuestAppDetailUiModel> =
        MutableStateFlow(QuestAppDetailUiModel())
    val uiModel: StateFlow<QuestAppDetailUiModel> = _uiModel.asStateFlow()

    init {
        val app = appListRepository.getCache(packageName)

        if (app != null) {

            _uiModel.value = QuestAppDetailUiModel(
                title = app.appName,
                iconDrawable = app.iconDrawable,
                packageName = app.packageName,
                storeName = app.storeName.name,
                questList = listOf()
            )
        }
    }
}