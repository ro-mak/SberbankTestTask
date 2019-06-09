package ru.makproductions.sberbanktesttask.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Translation(
    @Expose
    val text: Array<String>,
    @Expose
    @SerializedName("lang")
    val translationDirection: String
)