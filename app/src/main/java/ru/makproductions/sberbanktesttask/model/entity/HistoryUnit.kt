package ru.makproductions.sberbanktesttask.model.entity

import java.util.*

data class HistoryUnit(
    val originalText: String,
    val translationText: String,
    val date: Date,
    val languageOriginal: String,
    val languageTranslation: String
)
