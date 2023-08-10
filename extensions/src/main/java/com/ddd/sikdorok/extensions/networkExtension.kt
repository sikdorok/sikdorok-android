package com.ddd.sikdorok.extensions

val Int.NotFound: Boolean
    get() = this == 404

val Int.Unauthorized: Boolean
    get() = this == 401

val Int.Forbidden: Boolean
    get() = this == 403

val Int.TimeOut: Boolean
    get() = this == 408

val Int.BadGateway: Boolean
    get() = this == 502

val Int.InternalSeverError: Boolean
    get() = this == 500

