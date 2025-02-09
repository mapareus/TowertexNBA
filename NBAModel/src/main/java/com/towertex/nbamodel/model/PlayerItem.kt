package com.towertex.nbamodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "player_items",
    primaryKeys = [
        "id"
    ],
    indices = [
        Index("id"),
    ],
)
class PlayerItem (
    @ColumnInfo(name = "page")
    val page: Int, //artificial
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "position")
    val position: String,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "weight")
    val weight: String,
    @ColumnInfo(name = "jersey_number")
    val jerseyNumber: String,
    @ColumnInfo(name = "college")
    val college: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "draft_year")
    val draftYear: Int?,
    @ColumnInfo(name = "draft_round")
    val draftRound: Int?,
    @ColumnInfo(name = "draft_number")
    val draftNumber: Int?,
    @ColumnInfo(name = "team_id")
    val teamId: Int,
)