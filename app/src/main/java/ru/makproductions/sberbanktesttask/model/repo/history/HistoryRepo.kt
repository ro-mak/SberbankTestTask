package ru.makproductions.sberbanktesttask.model.repo.history

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.cache.ICache
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit

class HistoryRepo(val cache: ICache) : IHistoryRepo {
    override fun loadHistory(): Single<List<HistoryUnit>> {
        return cache.loadHistory()
    }
}