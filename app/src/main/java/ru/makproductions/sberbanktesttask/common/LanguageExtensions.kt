package ru.makproductions.sberbanktesttask.common

import ru.makproductions.sberbanktesttask.model.entity.YandexSupportedLanguage


fun YandexSupportedLanguage.toMap(): Map<String, String> {
    val mapOfLanguages = mutableMapOf<String, String>()
    val fields = YandexSupportedLanguage::class.java.declaredFields
    for (field in fields) {
        field.isAccessible = true
        mapOfLanguages.put(field.name, field.get(this) as String)
    }
    return mapOfLanguages
}

