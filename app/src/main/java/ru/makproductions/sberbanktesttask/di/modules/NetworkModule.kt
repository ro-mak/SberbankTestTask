package ru.makproductions.sberbanktesttask.di.modules

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.makproductions.sberbanktesttask.model.network.ITranslationNetService
import timber.log.Timber

fun getApiBaseUrl(): String {
    return "https://translate.yandex.net/api/v1.5/tr.json/"
}

fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun getNetService(gson: Gson, baseUrl: String, okHttpClient: OkHttpClient): ITranslationNetService {
    Timber.e("getApiService")
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
        .create<ITranslationNetService>(ITranslationNetService::class.java)
}

fun getGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
}

fun loggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}