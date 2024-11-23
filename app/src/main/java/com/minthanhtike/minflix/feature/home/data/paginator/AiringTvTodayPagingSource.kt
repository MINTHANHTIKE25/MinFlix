package com.minthanhtike.minflix.feature.home.data.paginator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minthanhtike.minflix.feature.home.data.remote.HomeRemoteDataSource
import com.minthanhtike.minflix.feature.home.domain.model.AiringTvTodayModel

class AiringTvTodayPagingSource(
    private val homeRemoteDataSource: HomeRemoteDataSource
):PagingSource<Int,AiringTvTodayModel>() {
    override fun getRefreshKey(state: PagingState<Int, AiringTvTodayModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AiringTvTodayModel> {
        val nextPageNumber = params.key ?: 1
        return homeRemoteDataSource.getAirTodayTv(nextPageNumber)
            .fold(
                onSuccess = { tv ->
                    val result = tv.filter { it.posterPath.isNotEmpty() }
                    LoadResult.Page(
                        data = result,
                        prevKey = null,
                        nextKey = if (result.isNotEmpty() and (nextPageNumber < 5))
                            nextPageNumber + 1 else null
                    )
                },
                onFailure = { error ->
                    LoadResult.Error(error)
                }
            )
    }
}