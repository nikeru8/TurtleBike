package com.turtle.turtlebike

import android.util.Log
import com.turtle.turtlebike.api.YoubikeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideMainActivityRepository(youbikeService: YoubikeService): MainRepository {
        return MainRepository(youbikeService)
    }

    @Provides
    @Singleton
    fun provideYoubikeService(retrofit: Retrofit): YoubikeService {
        return retrofit.create(YoubikeService::class.java)
    }


    @Provides
    @Singleton
    fun provideYoubikeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://tcgbusfs.blob.core.windows.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    class LogJsonInterceptor : Interceptor {
        @Throws(IOException::class)

        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            val rawJson: String? = response.body()?.string()

            Log.d(
                "TAG",
                String.format(
                    "call: ${response.request().url()} \n  , RetrofitManager raw JSON response is: %s",
                    rawJson
                )
            )

            // Re-create the response before returning it because body can be read only once
            return response.newBuilder()
                .body(ResponseBody.create(response.body()?.contentType(), rawJson.toString())).build()
        }
    }

    @Provides
    @Singleton
    fun provideLogJsonInterceptor(): LogJsonInterceptor {

        return LogJsonInterceptor()

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: LogJsonInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    }


}