package com.gratus.adidasconfirmed.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gratus.adidasconfirmed.util.RxSchedulerRule
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
abstract class MockServerSetup<T> {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private var mockServer = MockWebServer()

    @Before
    fun setMockServer() {
        mockServer = MockWebServer()
        mockServer.start(8080)
    }

    fun enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("json-response/$fileName")

        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
            .setResponseCode(code)
            .setBody(source.readString(StandardCharsets.UTF_8))

        mockServer.enqueue(mockResponse)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    fun retrofitService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
            .create(serviceClass)
    }

    private fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @After
    fun stopServer() {
        mockServer.shutdown()
    }
}
