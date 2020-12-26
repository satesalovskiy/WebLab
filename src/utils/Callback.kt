package utils

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface Callback<T> {
    fun onComplete(result: T)
    fun onException(e: Exception?)
}

suspend fun <T> awaitCallback(block: (Callback<T>) -> Unit) : T =
        suspendCoroutine { cont ->
            block(object : Callback<T> {
                override fun onComplete(result: T) = cont.resume(result)
                override fun onException(e: Exception?) {
                    e?.let { cont.resumeWithException(it) }
                }
            })
        }

fun <A, T> toSuspendFunction (fn: (A, Callback<T>) -> Unit): suspend (A)-> T = { a: A ->
    awaitCallback { fn(a, it) }
}

fun <A, B, T> toSuspendFunction (fn: (A, B, Callback<T>) -> Unit): suspend (A, B)-> T = { a: A, b: B ->
    awaitCallback { fn(a, b, it) }
}