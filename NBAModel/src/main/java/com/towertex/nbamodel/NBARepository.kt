package com.towertex.nbamodel

import com.towertex.nbaapi.services.PlayersApiContract
import com.towertex.nbamodel.repositories.PlayerRepository
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import com.towertex.nbamodel.room.NBADatabase

//data model is divided into sections
//each section is implemented via dedicated delegate
//data model is using SingleSourceOfTruth architecture where the app gets only Room objects but never the Retrofit objects
//in the Demo app there is only 1 part of the model and it has 3 services
class NBARepository(playersApi: PlayersApiContract, db: NBADatabase) :
        PlayerRepositoryContract by PlayerRepository(playersApi, db.nbaDao)