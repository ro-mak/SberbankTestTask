package ru.makproductions.sberbanktesttask.model.repo.history

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit

interface IHistoryRepo {
    fun loadHistory(): Single<List<HistoryUnit>>
}