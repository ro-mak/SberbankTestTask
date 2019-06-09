package ru.makproductions.sberbanktesttask.view.history

interface HistoryItemView {
    var pos: Int?
    fun setTranslationText(translationText: String)
    fun setOriginalText(originalText: String)
}