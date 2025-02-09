package com.towertex.nba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towertex.nba.R
import com.towertex.nba.repository.GlideRepositoryContract
import com.towertex.nba.repository.ResourceRepositoryContract
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TeamDetailViewModel(
    private val teamId: Int,
    private val playerRepository: PlayerRepositoryContract,
    private val res: ResourceRepositoryContract,
    private val glideRes: GlideRepositoryContract,
): ViewModel() {
    private val unknown = res.getString(R.string.unknown)

    private val _fullName = MutableStateFlow(unknown)
    val fullName: StateFlow<String> get() = _fullName

    private val _name = MutableStateFlow(unknown)
    val name: StateFlow<String> get() = _name

    private val _shortName = MutableStateFlow(unknown)
    val shortName: StateFlow<String> get() = _shortName

    private val _city = MutableStateFlow(unknown)
    val city: StateFlow<String> get() = _city

    private val _division = MutableStateFlow(unknown)
    val division: StateFlow<String> get() = _division

    private val _glideUrl = MutableStateFlow("")
    val glideUrl: StateFlow<String> get() = _glideUrl

    init {
        viewModelScope.launch {
            val team = playerRepository.getTeam(teamId).filterNotNull().first()
            _fullName.value = res.getString(R.string.td_full_name, team.fullName)
            _name.value = res.getString(R.string.td_name, team.name)
            _shortName.value = res.getString(R.string.td_short_name, team.abbreviation)
            _city.value = res.getString(R.string.td_city, team.city)
            _division.value = res.getString(R.string.td_division, team.conference, team.division)
            _glideUrl.value = glideRes.getGlideUrl(team.abbreviation)
        }
    }
}