package ru.makproductions.sberbanktesttask.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.makproductions.sberbanktesttask.di.modules.cicerone
import ru.makproductions.sberbanktesttask.di.modules.navigatorHolder
import ru.makproductions.sberbanktesttask.di.modules.router
import ru.makproductions.sberbanktesttask.model.cache.ICache
import ru.makproductions.sberbanktesttask.model.repo.HistoryRepo
import ru.makproductions.sberbanktesttask.model.repo.IHistoryRepo
import ru.makproductions.sberbanktesttask.model.room.cache.RoomCache

val repoModule = module {
    single { HistoryRepo(get()) }.bind(IHistoryRepo::class)
}

val cacheModule = module {
    single { RoomCache() }.bind(ICache::class)
}

val ciceroneModule = module {
    single { cicerone() }
    single { router() }
    single { navigatorHolder() }
}