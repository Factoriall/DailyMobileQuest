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
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    appListRepository: AppListRepository
) : ViewModel() {
    private val packageName: String = savedStateHandle["packageName"] ?: ""

    private val _uiModel: MutableStateFlow<QuestAppDetailUiModel> =
        MutableStateFlow(QuestAppDetailUiModel())
    val uiModel: StateFlow<QuestAppDetailUiModel> = _uiModel.asStateFlow()

    init {
        Log.d("DetailViewModel", "packageName: $packageName")
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