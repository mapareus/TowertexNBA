package com.towertex.nba.di

import com.towertex.nba.repository.GlideRepository
import com.towertex.nba.repository.GlideRepositoryContract
import com.towertex.nba.repository.ResourceRepository
import com.towertex.nba.repository.ResourceRepositoryContract
import com.towertex.nba.viewmodel.PlayerListViewModel
import com.towertex.nba.viewmodel.PlayerCardViewModel
import com.towertex.nba.viewmodel.PlayerDetailViewModel
import com.towertex.nba.viewmodel.TeamDetailViewModel
import com.towertex.nbaapi.NBAApiBuilder
import com.towertex.nbaapi.services.PlayersApiContract
import com.towertex.nbamodel.NBARepository
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import com.towertex.nbamodel.room.NBADatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

const val PAGE_SIZE = 35
const val FIRST_PAGE = 1

val repositoryModule = module {
    single { NBADatabase.buildDatabase(context = get()) }
    single { NBAApiBuilder { enableLogging(); setPerPage(PAGE_SIZE) }.build() } bind PlayersApiContract::class
    single { NBARepository(playersApi = get(), db = get()) } bind PlayerRepositoryContract::class
    single { ResourceRepository(androidContext()) } bind ResourceRepositoryContract::class
    single { GlideRepository(res = get()) } bind GlideRepositoryContract::class
}

val viewModelModule = module {
    viewModelOf(::PlayerListViewModel)

    factory { (playerId: Int) -> PlayerCardViewModel(playerId = playerId, playerRepository = get(), res = get()) }
    factory { (playerId: Int) -> PlayerDetailViewModel(playerId = playerId, playerRepository = get(), res = get()) }
    factory { (teamId: Int) -> TeamDetailViewModel(teamId = teamId, playerRepository = get(), res = get(), glideRes = get()) }
}