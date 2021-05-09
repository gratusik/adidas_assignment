package com.gratus.adidasconfirmed.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gratus.adidasconfirmed.data.repository.ProductListDataSource
import com.gratus.adidasconfirmed.data.service.remote.ProductListService
import com.gratus.adidasconfirmed.domain.repository.ProductListRepository
import com.gratus.adidasconfirmed.util.constants.ServiceConstants
import com.gratus.adidasconfirmed.util.interceptor.AppInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class NetworkModule {
    // This is where the Interceptor object is constructed
    fun provideInterceptor(): AppInterceptor? {
        return AppInterceptor().get()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ServiceConstants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun getRequestHeader(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(AppInterceptor())
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
        return httpClient.build()
    }

    // service provider
    @Provides
    @Singleton
    fun provideProductListService(retrofit: Retrofit): ProductListService {
        return retrofit.create(ProductListService::class.java)
    }

    // repo provider
    @Singleton
    @Provides
    fun provideProductListRepository(
        productListService: ProductListService
    ): ProductListRepository {
        return ProductListDataSource(productListService)
    }
}
