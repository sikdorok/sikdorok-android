package com.ddd.sikdorok.shared.base

import com.google.gson.annotations.SerializedName

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()

    sealed class Failure() : ApiResult<Nothing>() {
        data class HttpError(
            @SerializedName("code")
            val code: Int,
            @SerializedName("message")
            val message: String,
            @SerializedName("field")
            val body: String
        ) : Failure()

        data class NetworkError(val throwable: Throwable) : Failure()

        data class UnknownApiError(val throwable: Throwable) : Failure()

        fun safeThrowable(): Throwable = when (this) {
            is HttpError -> IllegalStateException("$message $body")
            is NetworkError -> throwable
            is UnknownApiError -> throwable
        }
    }

    fun isSuccess(): Boolean = this is Success

    fun isFailure(): Boolean = this is Failure

    fun getOrThrow(): T {
        throwOnFailure()
        return (this as Success).data
    }

    fun getOrNull(): T? =
        when (this) {
            is Success -> data
            else -> null
        }

    fun failureOrThrow(): Failure.HttpError {
        throwOnSuccess()
        return this as? Failure.HttpError ?: Failure.HttpError(
            code = 400,
            message = "오류가 발생했습니다. 다시 시도해 주세요.",
            body = "오류가 발생했습니다. 다시 시도해 주세요."
        )
    }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> safeThrowable()
            else -> null
        }

    companion object {
        fun <R> successOf(result: R): ApiResult<R> = Success(result)
    }
}

internal fun ApiResult<*>.throwOnFailure() {
    if (this is ApiResult.Failure) throw safeThrowable()
}

internal fun ApiResult<*>.throwOnSuccess() {
    if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

inline fun <T> ApiResult<T>.onFailure(action: (error: ApiResult.Failure.HttpError) -> Unit): ApiResult<T> {
    if (isFailure()) action(failureOrThrow())
    return this
}

inline fun <T> ApiResult<T>.onSuccess(action: (value: T?) -> Unit): ApiResult<T> {
    if (isSuccess()) action(getOrNull())
    return this
}

data class BaseResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Int? = null
)