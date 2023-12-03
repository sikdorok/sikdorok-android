package com.ddd.sikdorok.domain.settings

import com.ddd.sikdorok.domain.repository.SettingsRepository
import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse
import javax.inject.Inject

class GetSettingsUserDeviceInfoUseCase @Inject constructor(
    private val settingsResponse: SettingsRepository
) {
    suspend operator fun invoke(
        version: String
    ): SikdorokResponse<UserDeviceInfo> {
        return settingsResponse.getUserDeviceInfo(version)
    }
}