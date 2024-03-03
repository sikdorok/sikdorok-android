package com.ddd.sikdorok.domain.settings

import com.ddd.sikdorok.domain.repository.SettingsRepository
import com.ddd.sikdorok.shared.base.ApiResult
import com.ddd.sikdorok.shared.base.BaseResponse
import javax.inject.Inject

class PetUserProfileUseCase @Inject constructor(
    private val settingsResponse: SettingsRepository
) {
    suspend operator fun invoke(nickName: String): ApiResult<BaseResponse> {
        return settingsResponse.putUserProfile(nickName)
    }
}