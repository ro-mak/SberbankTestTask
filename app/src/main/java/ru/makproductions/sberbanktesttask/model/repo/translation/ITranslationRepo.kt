package ru.makproductions.sberbanktesttask.model.repo.translation

import io.reactivex.Single
import ru.makproductions.sberbanktesttask.model.entity.Translation

interface ITranslationRepo {
    fun loadTranslation(line: String): Single<Translation>
}