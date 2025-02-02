package com.example.dailymobilequest.domain.repository

import android.content.Intent
import android.content.pm.PackageManager
import com.example.dailymobilequest.data.QuestAppProfile
import com.example.dailymobilequest.data.StoreName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 앱의 리스트를 가져오고 저장하는 저장소
 */
@Singleton
class AppListRepository @Inject constructor(
    private val packageManager: PackageManager
) {
    private val cacheMap: MutableMap<String, QuestAppProfile> = mutableMapOf()

    suspend fun fetchAppList(): List<QuestAppProfile> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        return withContext(Dispatchers.IO) {
            packageManager.queryIntentActivities(intent, 0).map {
                val storeName =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                        packageManager.getInstallSourceInfo(it.activityInfo.packageName).installingPackageName
                    } else {
                        packageManager.getInstallerPackageName(it.activityInfo.packageName)
                    }
                QuestAppProfile(
                    appName = it.loadLabel(packageManager).toString(),
                    iconDrawable = it.loadIcon(packageManager),
                    packageName = it.activityInfo.packageName,
                    storeName = StoreName.from(storeName)
                )
            }.also {
                cacheMap.putAll(it.associateBy { app -> app.packageName })
            }
        }
    }

    fun getCache(packageName: String): QuestAppProfile? {
        return cacheMap[packageName]
    }
}