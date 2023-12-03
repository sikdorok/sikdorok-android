package com.ddd.sikdorok.domain.settings

import com.ddd.sikdorok.domain.repository.SettingsRepository
import com.ddd.sikdorok.shared.UserDeviceInfo
import com.ddd.sikdorok.shared.base.SikdorokResponse
import javax.inject.Inject

class SetUserLogoutUseCase @Inject constructor(
    private val settingsResponse: SettingsRepository
) {
    suspend operator fun invoke(): SikdorokResponse<Boolean> {
        return settingsResponse.setUserLogout()
    }
}