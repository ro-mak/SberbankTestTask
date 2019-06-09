package ru.makproductions.sberbanktesttask.model.repo.history

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.sberbanktesttask.model.cache.ICache
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit

class HistoryRepo(val cache: ICache) : IHistoryRepo {
    override fun loadHistory(): Single<List<HistoryUnit>> {
        return cache.loadHistory().subscribeOn(Schedulers.io())
    }

    override fun deleteHistory() {
        cache.deleteHistory()
    }
}