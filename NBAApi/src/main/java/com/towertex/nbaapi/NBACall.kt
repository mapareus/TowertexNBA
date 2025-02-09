package com.towertex.nbaapi

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

//The custom retrofit call is used to have access the other parts of the call (e.g. header) and to parse the error response
@Suppress("unused")
class NBACall<TResponse>(private val original: Call<TResponse>) : Call<TResponse> {

    @Throws(IOException::class)
    override fun execute(): Response<TResponse> = transformResponse(original.execute())

    private fun transformResponse(response: Response<TResponse>): Response<TResponse> = when {
        response.isSuccessful -> response
        else -> with (response.code()) {
            throw NBAApiException(
                this,
                NBAApiErrorType.fromHttpCode(this),
                original.request().url.encodedPath
            )
        }
    }

    override fun enqueue(callback: Callback<TResponse>) {
        original.enqueue(object : Callback<TResponse> {
            override fun onResponse(call: Call<TResponse>, response: Response<TResponse>) {
                val transformed: Response<TResponse> =
                    try {
                        transformResponse(response)
                    } catch (t: Throwable) {
                        callback.onFailure(this@NBACall, t)
                        return
                    }
                callback.onResponse(this@NBACall, transformed)
            }

            override fun onFailure(call: Call<TResponse>, t: Throwable) {
                callback.onFailure(this@NBACall, t)
            }
        })
    }

    override fun isExecuted(): Boolean = original.isExecuted

    override fun cancel() { original.cancel() }

    override fun isCanceled(): Boolean = original.isCanceled

    override fun clone(): Call<TResponse> = NBACall(original.clone())

    override fun request(): Request = original.request()

    override fun timeout(): Timeout = original.timeout()
}