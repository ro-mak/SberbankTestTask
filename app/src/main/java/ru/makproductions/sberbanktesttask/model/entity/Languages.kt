package ru.makproductions.sberbanktesttask.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Languages(
    @Expose
    @SerializedName("dirs")
    val directions: Array<String>,
    @Expose
    @SerializedName("langs")
    val languages: YandexSupportedLanguage
)