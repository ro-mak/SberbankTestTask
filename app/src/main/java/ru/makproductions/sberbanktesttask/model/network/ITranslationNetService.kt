package ru.makproductions.sberbanktesttask.model.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.makproductions.sberbanktesttask.App
import ru.makproductions.sberbanktesttask.R
import ru.makproductions.sberbanktesttask.model.entity.Languages
import ru.makproductions.sberbanktesttask.model.entity.Translation

interface ITranslationNetService {
    //https://translate.yandex.net/api/v1.5/tr.json/getLangs?ui=en&key=
    //
    // ? [key=<API-ключ>]
    // & [text=<переводимый текст>]
    // & [lang=<направление перевода>]
    // & [format=<формат текста>]
    // & [options=<опции перевода>]
    @GET("translate")
    fun loadTranslation(
        @Query("key") key: String = App.instance.getString(R.string.translation_api_key),
        @Query("text") line: String, @Query("lang", encoded = true) translationDirection: String = "en-ru"
    ): Single<Translation>

    @GET("getLangs")
    fun loadLanguages(@Query("ui") locale: String, @Query("key") key: String = App.instance.getString(R.string.translation_api_key)): Single<Languages>
}