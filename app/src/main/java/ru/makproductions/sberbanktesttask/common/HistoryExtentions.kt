package ru.makproductions.sberbanktesttask.common

import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import java.util.*

fun HistoryUnit.toArray(): Array<String> = arrayOf(
    this.id,
    this.originalText,
    this.translationText,
    this.date.time.toString(),
    this.languageOriginal,
    this.languageTranslation
)

fun Array<String>.toHistoryUnit() = HistoryUnit(this[0], this[1], this[2], Date(this[3].toLong()), this[4], this[5])
