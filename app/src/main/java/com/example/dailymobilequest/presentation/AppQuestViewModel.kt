package com.example.dailymobilequest.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailymobilequest.data.AppData
import com.example.dailymobilequest.data.AppDataWithQuestList
import com.example.dailymobilequest.domain.repository.AppDataRepository
import com.example.dailymobilequest.domain.repository.AppListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 하나의 앱 퀘스트 모음 관련 뷰모델
 */
@HiltViewModel
class AppQuestViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    appListRepository: AppListRepository,
    private val appDataRepository: AppDataRepository
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

    fun saveData() {
        viewModelScope.launch {
            appDataRepository.insertAppData(
                AppDataWithQuestList(
                    AppData(
                        appName = _uiModel.value.title,
                        packageName = _uiModel.value.packageName,
                        storeName = _uiModel.value.storeName,
                    ),
                    questList = listOf() // TODO: _uiModel.value.questList.map { ... } 형태로 교체
                )
            )
        }
    }
}