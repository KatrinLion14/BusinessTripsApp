package com.example.businesstripsapp.create_office_activity.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "offices")
class OfficeDB {
    @PrimaryKey
    var id: String? = null

    var address: String? = null

    var description: String? = null

    override fun toString(): String {
        return "ID: " + id + "; ADDRESS: " + address + "; DESCRIPTION: " +
                description
    }
}