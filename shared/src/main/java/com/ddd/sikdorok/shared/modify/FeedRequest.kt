package com.ddd.sikdorok.shared.modify

import com.ddd.sikdorok.shared.code.Icon
import com.ddd.sikdorok.shared.code.Tag

data class FeedRequest(
    val tag: Tag,
    val time: String,
    val memo: String = "",
    val icon: Icon,
    val isMain: Boolean,
    val deletePhotoTokens: List<String> = emptyList()
)
