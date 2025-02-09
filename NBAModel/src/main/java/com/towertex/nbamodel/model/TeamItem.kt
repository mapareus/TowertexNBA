package com.towertex.nbamodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "team_items",
    primaryKeys = [
        "id"
    ],
    indices = [
        Index("id"),
    ],
)
class TeamItem (
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "conference")
    val conference: String,
    @ColumnInfo(name = "division")
    val division: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "abbreviation")
    val abbreviation: String,
)