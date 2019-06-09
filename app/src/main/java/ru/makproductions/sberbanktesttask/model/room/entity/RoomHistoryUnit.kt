package ru.makproductions.sberbanktesttask.model.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class RoomHistoryUnit(
    @PrimaryKey var id: String,
    var originalText: String,
    var translationText: String,
    var creationDate: Date,
    var originalLanguage: String,
    var translationLanguage: String
)