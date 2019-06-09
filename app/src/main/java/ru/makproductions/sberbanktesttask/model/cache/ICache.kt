package ru.makproductions.sberbanktesttask.model.cache

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit

interface ICache {
    fun loadHistory(): Single<List<HistoryUnit>>
    fun saveHistoryUnit(historyUnit: HistoryUnit)
    fun deleteHistory()
}