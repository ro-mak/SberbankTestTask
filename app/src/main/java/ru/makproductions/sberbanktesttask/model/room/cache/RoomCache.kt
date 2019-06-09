package ru.makproductions.sberbanktesttask.model.room.cache

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.makproductions.sberbanktesttask.model.cache.ICache
import ru.makproductions.sberbanktesttask.model.entity.HistoryUnit
import ru.makproductions.sberbanktesttask.model.room.db.HistoryDatabase
import ru.makproductions.sberbanktesttask.model.room.entity.RoomHistoryUnit
import timber.log.Timber

class RoomCache : ICache {
    override fun loadHistory(): Single<List<HistoryUnit>> {
        return Single.create { emitter ->
            val roomHistory = HistoryDatabase.getInstance()?.historyDao?.loadHistory()
            val history: MutableList<HistoryUnit> = mutableListOf()
            roomHistory?.let {
                for (roomUnit in it) {
                    history.add(
                        HistoryUnit(
                            roomUnit.id,
                            roomUnit.originalText,
                            roomUnit.translationText,
                            roomUnit.creationDate,
                            roomUnit.originalLanguage,
                            roomUnit.translationLanguage
                        )
                    )
                }
                Timber.e("History loaded size = " + history.size)
                emitter.onSuccess(history)
            } ?: let { Timber.e("History load failed. May be big bang just banged?") }
        }
    }

    override fun saveHistoryUnit(historyUnit: HistoryUnit) {
        Completable.create { emitter ->
            val roomUnit = RoomHistoryUnit(
                historyUnit.id,
                historyUnit.originalText,
                historyUnit.translationText,
                historyUnit.date,
                historyUnit.languageOriginal,
                historyUnit.languageTranslation
            )
            HistoryDatabase.getInstance()?.historyDao?.insert(roomUnit)
            Timber.e("HistoryUnit saved")
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun deleteHistory() {
        Completable.create { emitter ->
            HistoryDatabase.getInstance()?.historyDao?.deleteAll()
            Timber.e("History deleted")
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}