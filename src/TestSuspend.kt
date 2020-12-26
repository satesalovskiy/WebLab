import kotlinx.browser.window
import kotlin.coroutines.*
import kotlin.js.Promise

private object JavaScriptContext : AbstractCoroutineContextElement(ContinuationInterceptor.Key), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>) = object : Continuation<T> {
        override val context = continuation.context

        override fun resumeWith(result: Result<T>) {
            if (result.isSuccess) {
                window.setTimeout({ continuation.resume(result.getOrNull()!!) }, 0)
            } else {
                window.setTimeout({ continuation.resumeWithException(result.exceptionOrNull()!!) }, 0)
            }
        }
    }
}

fun <T> immediateAsync(c: suspend () -> T) {
    c.startCoroutine(object : Continuation<T> {
        override val context = JavaScriptContext

        override fun resumeWith(result: Result<T>) {
            if(result.isFailure){
                throw result.exceptionOrNull()!!
            }
        }
    })
}

fun <T> promiseAsync(c: suspend () -> T): Promise<T> {
    return Promise { resolve, reject ->
        c.startCoroutine(object : Continuation<T> {
            override val context = EmptyCoroutineContext

            override fun resumeWith(result: Result<T>) {
                return if(result.isSuccess){
                    resolve(result.getOrNull()!!)
                }else{
                    reject(result.exceptionOrNull()!!)
                }
            }
        })
    }
}

suspend inline fun <T> Promise<T>.await() = suspendCoroutine<T> { c ->
    then({
        console.log("Resolving with $it")
        c.resume(it)
    }, {
        console.log("Rejecting with $it")
        c.resumeWithException(it)
    })
}

suspend fun <T> promise(value: T): Promise<T> {
    return Promise.resolve(value)
}