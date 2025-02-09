package com.towertex.nbamodel.repositories

import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.model.TeamItem
import kotlinx.coroutines.flow.Flow

interface PlayerRepositoryContract {
    fun getPlayers(page: Int): Flow<List<PlayerItem>>
    fun getPlayer(id: Int): Flow<PlayerItem?>
    fun getTeam(id: Int): Flow<TeamItem?>
}