package com.ddd.sikdorok.shared.base

@Suppress("SpellCheckingInspection")
data class SikdorokResponse<out T: Response>(
    val code: Int,
    val message: String,
    val data: T
)

interface Response
