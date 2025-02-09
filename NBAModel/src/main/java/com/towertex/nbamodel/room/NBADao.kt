package com.towertex.nbamodel.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.model.TeamItem

@Dao
interface NBADao {
    //Player queries
    @Query("SELECT * FROM player_items WHERE page = :page")
    suspend fun getPlayers(page: Int): List<PlayerItem>
    @Query("SELECT * FROM player_items")
    suspend fun getPlayers(): List<PlayerItem>
    @Query("DELETE FROM player_items WHERE id IN (:ids)")
    suspend fun deletePlayers(ids: List<Int>)
    @Query("DELETE FROM player_items")
    suspend fun deletePlayers()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(items: List<PlayerItem>)
    @Query("SELECT * FROM player_items WHERE id = :id LIMIT 1")
    suspend fun getPlayer(id: Int): PlayerItem?

    //Team queries
    @Query("SELECT * FROM team_items")
    suspend fun getTeams(): List<TeamItem>
    @Query("DELETE FROM team_items WHERE id IN (:ids)")
    suspend fun deleteTeams(ids: List<Int>)
    @Query("DELETE FROM team_items")
    suspend fun deleteTeams()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(items: List<TeamItem>)
    @Query("SELECT * FROM team_items WHERE id = :id LIMIT 1")
    suspend fun getTeam(id: Int): TeamItem?
}