package com.netlight.cleanarchitecturesample.domain.exception

class ItemNotDeletedException(override val message: String?) : Exception(message)