package com.mavenusrs.vehicles.features.common

class StatefulResource<out T>(
    val status: Status,
    val data: T? = null,
    val errorMessage: String? = null,
    val throwable: Throwable? = null
) {
    enum class Status {
        LOADING,
        IS_EMPTY,
        FAILED,
        SUCCESSES
    }

}