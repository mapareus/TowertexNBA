package com.towertex.nba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towertex.nba.R
import com.towertex.nba.repository.ResourceRepositoryContract
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlayerCardViewModel(
    private val playerId: Int,
    private val playerRepository: PlayerRepositoryContract,
    private val res: ResourceRepositoryContract,
): ViewModel() {
    private val unknown = res.getString(R.string.unknown)

    private val _name = MutableStateFlow(unknown)
    val name: StateFlow<String> get() = _name

    private val _position = MutableStateFlow(unknown)
    val position: StateFlow<String> get() = _position

    private val _index = MutableStateFlow(unknown)
    val index: StateFlow<String> get() = _index

    init {
        viewModelScope.launch {
            val player = playerRepository.getPlayer(playerId).filterNotNull().first()
            _name.value = res.getString(R.string.pc_name, player.firstName, player.lastName)
            val team = playerRepository.getTeam(player.teamId).filterNotNull().first()
            _position.value = res.getString(R.string.pc_position, player.position, team.name)
            _index.value = res.getString(R.string.pc_index, player.id, player.page)
        }
    }
}