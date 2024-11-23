package com.minthanhtike.minflix.feature.home.data.paginator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minthanhtike.minflix.feature.home.data.remote.HomeRemoteDataSource
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels

class TrendingTvPagingSource(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private val time: String
) : PagingSource<Int, TrendingTvModels>() {
    override fun getRefreshKey(state: PagingState<Int, TrendingTvModels>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingTvModels> {
        val nextPageNumber = params.key ?: 1
        return homeRemoteDataSource.getTrendingTv(time, nextPageNumber)
            .fold(
                onSuccess = { movies ->
                    LoadResult.Page(
                        data = movies.filter { it.posterPath.isNotEmpty() },
                        prevKey = null,
                        nextKey = if (movies.isNotEmpty() and (nextPageNumber < 5))
                            nextPageNumber + 1 else null
                    )
                },
                onFailure = { error ->
                    LoadResult.Error(error)
                }
            )
    }
}