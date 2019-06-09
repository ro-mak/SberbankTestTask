package ru.makproductions.sberbanktesttask.model.repo.history

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit

interface IHistoryRepo {
    fun loadHistory(): Single<List<HistoryUnit>>
    fun deleteHistory()
    fun getSavedHistory(): List<HistoryUnit>
    fun saveHistory(history: List<HistoryUnit>)
}