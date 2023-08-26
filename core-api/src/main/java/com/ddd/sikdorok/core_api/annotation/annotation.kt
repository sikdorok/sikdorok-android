package com.ddd.sikdorok.core_api.annotation

import javax.inject.Qualifier


@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
internal annotation class LoggingInterceptor

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
internal annotation class HeaderInterceptor

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
internal annotation class RefreshInterceptor
