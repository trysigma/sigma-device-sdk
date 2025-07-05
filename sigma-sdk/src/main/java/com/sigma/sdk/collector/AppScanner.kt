package com.sigma.sdk.collector

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Модель одного установленного приложения.
 */
data class InstalledAppInfo(
    val packageName: String,
    val appName: String,
    val versionName: String?,
    val versionCode: Long,
    val firstInstallTime: Long,
    val lastUpdateTime: Long,
    val isSystem: Boolean,
    val category: String? = null,
)

/**
 * Лёгкий, suspend-дружелюбный сканер списка установленных приложений.
 *
 *  • Выполняется на Dispatchers.Default, UI-поток не блокируется.  
 *  • Категория доступна с API 26; на старых версиях будет null.  
 *  • Системные приложения по умолчанию фильтруются, чтобы уменьшить объём данных.  
 *  • Перед отправкой зашифруйте / захэшируйте packageName — это PII.
 *
 * ⚠️ Начиная с Android 11 (API 30) доступ к полному списку пакетов требует
 *    `<queries>` в манифесте или разрешения QUERY_ALL_PACKAGES (подлежит
 *    одобрению в Google Play Console). См. README.
 */
object AppScanner {

    /**
     * @param includeSystemApps  true — оставить системные приложения,
     *                           false (по умолчанию) — убрать.
     */
    suspend fun scan(
        context: Context,
        includeSystemApps: Boolean = false,
    ): List<InstalledAppInfo> = withContext(Dispatchers.Default) {

        val pm = context.packageManager

        @Suppress("DEPRECATION")
        val packages: List<PackageInfo> =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getInstalledPackages(PackageManager.PackageInfoFlags.of(0))
            } else {
                pm.getInstalledPackages(PackageManager.GET_META_DATA)
            }

        buildList {
            for (pkg in packages) {
                val ai = pkg.applicationInfo ?: continue
                val isSystem = (ai.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                if (!includeSystemApps && isSystem) continue

                val category: String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val cat = ai.category
                    if (cat != ApplicationInfo.CATEGORY_UNDEFINED) {
                        ApplicationInfo.getCategoryTitle(context, cat)?.toString()
                    } else null
                } else null

                add(
                    InstalledAppInfo(
                        packageName      = pkg.packageName,
                        appName          = ai.loadLabel(pm).toString(),
                        versionName      = pkg.versionName,
                        versionCode      = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                                               pkg.longVersionCode
                                           else
                                               pkg.versionCode.toLong(),
                        firstInstallTime = pkg.firstInstallTime,
                        lastUpdateTime   = pkg.lastUpdateTime,
                        isSystem         = isSystem,
                        category         = category,
                    )
                )
            }
        }
    }
}

/**
 * Пример вызова из host-приложения или обёртки SDK.
 *
 * ```kotlin
 * lifecycleScope.launch {
 *     val apps = AppScanner.scan(applicationContext)
 *     SigmaAnalytics.trackInstalledApps(apps) // ваш шифр-и-отправка слой
 * }
 * ```
 */
