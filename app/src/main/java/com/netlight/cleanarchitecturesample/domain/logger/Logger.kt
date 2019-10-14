package com.netlight.cleanarchitecturesample.domain.logger

interface Logger {

    fun i(tag: String, message: String)

    fun d(tag: String, message: String)

    fun w(tag: String, message: String? = null, throwable: Throwable? = null)

    fun e(tag: String, message: String, throwable: Throwable? = null)
}