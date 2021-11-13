package com.mavenusrs.vehicles.common

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Failed<T>(
        val data: Throwable? = null,
        val failureType: FailureType? = FailureType.GENERAL_FAILURE
    ) : Resource<T>()

    enum class FailureType {
        DATA_FAILURE,
        SERVER_FAILURE,
        NETWORK_FAILURE,
        GENERAL_FAILURE
    }
}