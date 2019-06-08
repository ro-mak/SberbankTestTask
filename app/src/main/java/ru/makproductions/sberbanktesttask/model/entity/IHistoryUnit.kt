package ru.makproductions.sberbanktesttask.model.entity

import java.util.*

interface IHistoryUnit {
    val word: String
    val translation: String
    val date: Date
    val languageOriginal: String
    val languageTranslation: String
}