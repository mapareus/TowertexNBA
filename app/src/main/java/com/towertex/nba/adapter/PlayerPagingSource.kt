package com.towertex.nba.adapter

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.towertex.nba.di.FIRST_PAGE
import com.towertex.nba.di.PAGE_SIZE
import com.towertex.nbamodel.model.PlayerItem
import com.towertex.nbamodel.repositories.PlayerRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first

class PlayerPagingSource(
    private val repository: PlayerRepositoryContract
) : PagingSource<Int, PlayerItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlayerItem> {
        return try {
            val pageNumber: Int = params.key ?: FIRST_PAGE
            val response = withContext(Dispatchers.IO) { repository.getPlayers(pageNumber).first() }
            //TODO Timber
            Log.d("data", "$pageNumber ... ${response.size}")
            LoadResult.Page(
                data = response,
                prevKey = if (pageNumber == FIRST_PAGE) null else pageNumber - 1,
                nextKey = if (response.size < PAGE_SIZE) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlayerItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        //TODO compare behavior
//        return state.closestPageToPosition(anchorPosition)?.prevKey
        val pageIndex = anchorPosition / PAGE_SIZE
        return pageIndex
    }
}