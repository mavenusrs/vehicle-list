package com.mavenusrs.vehicles.common

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

abstract class BaseUseCase<in Param, out T> where T : Any? {

    abstract suspend fun run(param: Param): Flow<Resource<T>>

    open operator fun invoke(
        job: Job,
        param: Param,
        onResult: (Resource<T>) -> Unit = {}
    ) {

        val bgJob = CoroutineScope(job + Dispatchers.IO).async { run(param) }

        CoroutineScope(job + Dispatchers.Main).launch {
            val await = bgJob.await()
            await.catch {
                onResult(Resource.Failed(it))
            }.collect {
                onResult(it)
            }
        }
    }

}