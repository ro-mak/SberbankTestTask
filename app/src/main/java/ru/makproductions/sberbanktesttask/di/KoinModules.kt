package ru.makproductions.sberbanktesttask.di

import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.makproductions.sberbanktesttask.di.modules.*
import ru.makproductions.sberbanktesttask.model.cache.ICache
import ru.makproductions.sberbanktesttask.model.repo.history.HistoryRepo
import ru.makproductions.sberbanktesttask.model.repo.history.IHistoryRepo
import ru.makproductions.sberbanktesttask.model.repo.translation.ITranslationRepo
import ru.makproductions.sberbanktesttask.model.repo.translation.TranslationRepo
import ru.makproductions.sberbanktesttask.model.room.cache.RoomCache

val repoModule = module {
    single { HistoryRepo(get()) }.bind(IHistoryRepo::class)
    single { TranslationRepo(get(), get()) }.bind(ITranslationRepo::class)
}

val cacheModule = module {
    single { RoomCache() }.bind(ICache::class)
}

val ciceroneModule = module {
    single { cicerone() }
    single { router() }
    single { navigatorHolder() }
}

val networkModule = module {
    single { getNetService(get(), get(named("baseUrl")), get()) }
    single(named("baseUrl")) { getApiBaseUrl() }
    single { getGson() }
    single { loggingInterceptor() }
    single { okHttpClient(get()) }
}
