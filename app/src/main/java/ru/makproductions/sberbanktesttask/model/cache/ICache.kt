package ru.makproductions.sberbanktesttask.model.cache

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.IHistoryUnit

interface ICache {
    fun loadHistory(): Single<List<IHistoryUnit>>
}