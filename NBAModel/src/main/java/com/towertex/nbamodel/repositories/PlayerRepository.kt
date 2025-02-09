package com.towertex.nbamodel.repositories

import com.towertex.nbaapi.model.PlayerObject
import com.towertex.nbaapi.model.TeamObject
import com.towertex.nbaapi.services.PlayersApiContract
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.model.TeamItem
import com.towertex.nbamodel.room.NBADao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayerRepository(
    private val api: PlayersApiContract,
    private val dao: NBADao
): PlayerRepositoryContract {
    override fun getPlayers(page: Int): Flow<List<PlayerItem>> = flow {
        val currentPlayers = dao.getPlayers(page)
        if (currentPlayers.isNotEmpty()) emit(currentPlayers)
        val newPlayers = api.getAllPlayers(page).execute().body()?.data ?: emptyList()
        if (newPlayers.isEmpty()) {
            if (currentPlayers.isNotEmpty()) return@flow
            emit(emptyList())
            return@flow
        }
        if (currentPlayers.equals(newPlayers, page)) return@flow
        val transformedNewPlayers = newPlayers.map { it.toPlayerItem(page) }
        dao.deletePlayers(currentPlayers.map { it.id })
        dao.insertPlayers(transformedNewPlayers)
        val transformedNewTeams = newPlayers.map { it.team }.distinct().map { it.toTeamItem() }
        dao.insertTeams(transformedNewTeams)
        emit(transformedNewPlayers)
    }

    private fun List<PlayerItem>.equals(new: List<PlayerObject>, page: Int): Boolean {
        if (size != new.size) return false
        sortedBy { it.id }.zip(new.sortedBy { it.id }).forEach {
            if (it.first.page != page) return false
            if (it.first.id != it.second.id) return false
        }
        return true
    }

    private fun TeamObject.toTeamItem() = TeamItem(
        id,
        abbreviation,
        city,
        conference,
        division,
        full_name,
        name
    )

    private fun PlayerObject.toPlayerItem(page: Int) = PlayerItem(
        page,
        id,
        first_name,
        last_name,
        position,
        height,
        weight,
        jersey_number,
        college,
        country,
        draft_year,
        draft_round,
        draft_number,
        team.id
    )

    override fun getPlayer(id: Int): Flow<PlayerItem?> = flow {
        emit(id.let { dao.getPlayer(it) })
    }

    override fun getTeam(id: Int): Flow<TeamItem?> = flow {
        emit(id.let { dao.getTeam(it) })
    }
}