package com.minthanhtike.minflix.feature.detail.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minthanhtike.minflix.feature.detail.data.mapper.toDomain
import com.minthanhtike.minflix.feature.detail.data.remote.HomeDetailRemoteDataSource
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel

class RecommendationPagingSource(
    private val homeDetailRemoteDataSource: HomeDetailRemoteDataSource,
    private val id: Int
) : PagingSource<Int, HomeDetailRecommendModel>() {
    override fun getRefreshKey(state: PagingState<Int, HomeDetailRecommendModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeDetailRecommendModel> {
        val nextPageNumber = params.key ?: 1
        val data = homeDetailRemoteDataSource
            .getRecommendMovies(movieId = id, page = nextPageNumber)
            .map {
                it.results?.filterNotNull()
                    ?.map { result -> result.toDomain() }.orEmpty()
            }
        return data.fold(
                onSuccess = { movies ->
                    val result = movies
                        .filter { it.posterPath.isNotEmpty() and it.title.isNotEmpty() }
                        .distinct()
                    LoadResult.Page(
                        data = result,
                        prevKey = null,
                        nextKey = if (result.isNotEmpty())
                            nextPageNumber + 1 else null
                    )
                },
                onFailure = { error ->
                    LoadResult.Error(error)
                }
            )
    }

}