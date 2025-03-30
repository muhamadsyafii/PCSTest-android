package id.syafii.pcstest.di
/*
 * Created by Muhamad Syafii
 * 30/3/2025 - muhamadsyafii4@gmail.com
 * Copyright (c) 2025.
 * All Rights Reserved
 */

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.syafii.pcstest.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    val authInterceptor = Interceptor { chain ->
      val request = chain.request().newBuilder()
        .addHeader("Accept", "application/json")
        .build()
      chain.proceed(request)
    }

    val builder =  OkHttpClient.Builder()
      .retryOnConnectionFailure(true)
      .cache(Cache(context.cacheDir, (10 * 1024 * 1024).toLong()))
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .writeTimeout(60, TimeUnit.SECONDS)
      .addInterceptor(loggingInterceptor)
      .addInterceptor(authInterceptor)

    if (BuildConfig.DEBUG){
      builder.addInterceptor(getChuckerInterceptor(context = context))
    }
    return builder.build()
  }

  private fun getChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context).build()
  }

  @Provides
  fun getRetrofit(client: OkHttpClient): Retrofit {
    val baseUrl = "https://66b197c51ca8ad33d4f482c9.mockapi.io"
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }


}