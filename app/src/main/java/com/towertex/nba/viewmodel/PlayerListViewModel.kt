package com.towertex.nba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.towertex.nba.adapter.PlayerPagingSource
import com.towertex.nba.di.PAGE_SIZE
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PlayerListViewModel(
    private val playerRepository: PlayerRepositoryContract
): ViewModel() {

    private val _players = MutableStateFlow<PagingData<PlayerItem>?>(null)
    val players: Flow<PagingData<PlayerItem>> get() = _players.filterNotNull()
    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { PlayerPagingSource(repository = playerRepository) }
            ).flow.cachedIn(viewModelScope).collect {
                _players.value = it
            }
        }
    }
}