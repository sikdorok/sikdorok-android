package com.ddd.sikdorok.domain.settings

import com.ddd.sikdorok.domain.repository.SettingsRepository
import com.ddd.sikdorok.shared.AppInfoRes
import com.ddd.sikdorok.shared.base.ApiResult
import javax.inject.Inject

class GetAppInfoUseCase @Inject constructor(
    private val settingsResponse: SettingsRepository
) {
    suspend operator fun invoke(): ApiResult<AppInfoRes> {
        return settingsResponse.getAppInfo()
    }
}