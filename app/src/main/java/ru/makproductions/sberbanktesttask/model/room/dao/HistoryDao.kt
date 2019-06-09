package ru.makproductions.sberbanktesttask.model.room.dao

import android.arch.persistence.room.*
import ru.makproductions.sberbanktesttask.model.room.entity.RoomHistoryUnit

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomHistoryUnit: RoomHistoryUnit)

    @Update
    fun update(roomHistoryUnit: RoomHistoryUnit)

    @Delete
    fun delete(roomHistoryUnit: RoomHistoryUnit)

    @Query("SELECT * FROM roomhistoryunit WHERE id = :id LIMIT 1")
    fun findHistoryUnit(id: String): RoomHistoryUnit

    @Query("SELECT * FROM roomhistoryunit")
    fun loadHistory(): List<RoomHistoryUnit>

    @Query("DELETE FROM roomhistoryunit")
    fun deleteAll()
}