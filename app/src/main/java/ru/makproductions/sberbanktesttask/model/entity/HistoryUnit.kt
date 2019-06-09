package ru.makproductions.sberbanktesttask.model.entity

import java.util.*

data class HistoryUnit(
    val id: String,
    val originalText: String,
    val translationText: String,
    val date: Date,
    val languageOriginal: String,
    val languageTranslation: String
)
