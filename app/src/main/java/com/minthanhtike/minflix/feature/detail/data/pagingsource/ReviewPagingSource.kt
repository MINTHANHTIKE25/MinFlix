package com.minthanhtike.minflix.feature.detail.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minthanhtike.minflix.feature.detail.data.mapper.toDomain
import com.minthanhtike.minflix.feature.detail.data.remote.HomeDetailRemoteDataSource
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel

class ReviewPagingSource(
    private val homeDetailRemoteDataSource: HomeDetailRemoteDataSource,
    private val id: Int
) : PagingSource<Int, HomeDetailReviewModel>() {
    override fun getRefreshKey(state: PagingState<Int, HomeDetailReviewModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeDetailReviewModel> {
        val nextPageNumber = params.key ?: 1
        val data = homeDetailRemoteDataSource.getMovieReviews(movieId = id, page = nextPageNumber)
            .map { it.results?.filterNotNull()?.map { result -> result.toDomain() }.orEmpty() }
        return data.fold(
            onSuccess = { movies ->
                val result = movies
                    .filter {
                        it.content.isNotEmpty() and it.author.isNotEmpty()
                    }
                    .distinctBy { it.id }
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