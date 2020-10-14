package cn.wp2app.model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import cn.wp2app.model.Result

open class Repository {

    suspend fun <T : Any> apiCall(call: suspend () -> T): T {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            cn.wp2app.model.Result.Error(IOException(errorMessage, e))
        }
    }

    /*
    suspend fun <T : Any> executeResponse(response: T, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {
            if (response. == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                Result.Success(response.data)
            }
        }
    }
   */

}