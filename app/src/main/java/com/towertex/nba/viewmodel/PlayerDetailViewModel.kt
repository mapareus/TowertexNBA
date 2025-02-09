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

class PlayerDetailViewModel(
    private val playerId: Int,
    private val playerRepository: PlayerRepositoryContract,
    private val res: ResourceRepositoryContract,
): ViewModel() {
    private val unknown = res.getString(R.string.unknown)

    private val _name = MutableStateFlow(unknown)
    val name: StateFlow<String> get() = _name

    private val _position = MutableStateFlow(unknown)
    val position: StateFlow<String> get() = _position

    private val _body = MutableStateFlow(unknown)
    val body: StateFlow<String> get() = _body

    private val _number = MutableStateFlow(unknown)
    val number: StateFlow<String> get() = _number

    private val _college = MutableStateFlow(unknown)
    val college: StateFlow<String> get() = _college

    private val _draft = MutableStateFlow(unknown)
    val draft: StateFlow<String> get() = _draft

    private val _teamId = MutableStateFlow<Int?>(null)
    val teamId: StateFlow<Int?> get() = _teamId

    init {
        viewModelScope.launch {
            val player = playerRepository.getPlayer(playerId).filterNotNull().first()
            _teamId.value = player.teamId
            val team = playerRepository.getTeam(player.teamId).filterNotNull().first()
            _name.value = res.getString(R.string.pd_name, player.firstName, player.lastName)
            _position.value = res.getString(R.string.pd_position, player.position, team.name)
            _body.value = res.getString(R.string.pd_body, player.height, player.weight)
            _number.value = res.getString(R.string.pd_number, player.jerseyNumber)
            _college.value = res.getString(R.string.pd_college, player.college, player.country)
            _draft.value = res.getString(R.string.pd_draft, player.draftYear ?: 0, player.draftRound ?: 0, player.draftNumber ?: 0)
        }
    }
}