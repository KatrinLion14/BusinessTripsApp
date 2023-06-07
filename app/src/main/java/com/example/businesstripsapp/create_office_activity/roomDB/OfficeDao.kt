package com.example.businesstripsapp.create_office_activity.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface OfficeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffice(officeDB: OfficeDB?): Long

    @Query("DELETE FROM offices WHERE id LIKE :id")
    fun deleteById(id: String)

    @Query("SELECT * FROM offices WHERE id LIKE :id")
    fun findOfficeById(id: String): List<OfficeDB?>?

    @Query("SELECT `id` FROM offices")
    fun idsList(): List<String?>?

    @Query("SELECT * FROM offices")
    fun getAll(): List<OfficeDB?>?
}
