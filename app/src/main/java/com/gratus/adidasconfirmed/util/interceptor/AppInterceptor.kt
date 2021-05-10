@file:Suppress(
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.gratus.adidasconfirmed.util.interceptor

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import javax.inject.Inject
import javax.inject.Singleton

// Interceptor to change the Base URL dynamically
// to accomodate product list and details url and add review url
@Singleton
class AppInterceptor @Inject constructor() : Interceptor {
    private var sInterceptor: AppInterceptor? = null
    private var mScheme: String? = null
    private var mHost: String? = null
    private var mPort: Int? = null

    @Inject
    fun get(): AppInterceptor? {
        if (sInterceptor == null) {
            sInterceptor = AppInterceptor()
        }
        return sInterceptor
    }

    fun setInterceptor(url: String?) {
        val httpUrl = url!!.toHttpUrlOrNull()
        mScheme = httpUrl!!.scheme
        mHost = httpUrl.host
        mPort = httpUrl.port
    }

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        // If new Base URL is properly formatted than replace with old one
        if (mScheme != null && mHost != null && mPort != null) {
            val newUrl: HttpUrl = original.url.newBuilder()
                .scheme(mScheme!!)
                .host(mHost!!)
                .port(mPort!!)
                .build()
            original = original.newBuilder()
                .url(newUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build()
            logger.info(
                String.format(
                    "Sending request %s on %s%n%s",
                    original.url, chain.connection(), original.headers
                )
            )
        }
        return chain.proceed(original)
    }
}
