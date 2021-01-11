import kotlin.coroutines.*
import kotlin.js.Promise

// Simplistic Promise/Coroutine bridge, based on this:
// https://discuss.kotlinlang.org/t/async-await-on-the-client-javascript/2412

// Start a suspendable coroutine from a sync context.
fun launchAsync(block: suspend () -> Unit) {
    block.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext get() = EmptyCoroutineContext
        override fun resumeWith(result: Result<Unit>) {
            if (result.isFailure) {
                println("Unhandled Promise exception: ${result.exceptionOrNull()}")
            }
        }
    })
}

// Explicitly "await" on a Node Promise<>. This just translates the Promise
// .then() into Kotlin coroutines.
suspend fun <T> Promise<T>.await(): T = suspendCoroutine { cont ->
    then({ cont.resume(it) }, { cont.resumeWithException(it) })
}
