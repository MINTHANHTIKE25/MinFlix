package com.minthanhtike.minflix.feature.home.data.paginator

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.minthanhtike.minflix.exception.ApiException
import com.minthanhtike.minflix.feature.home.data.remote.HomeRemoteDataSource
import com.minthanhtike.minflix.feature.home.data.remote.Result
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels

class TrendingTvPagingSource<T : Any>(
    private val pagingData: suspend (page: Int, pageSize: Int) -> Result<List<T>>
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
        (params.key ?: 1).let { _page ->
            try {
                pagingData(_page, params.loadSize)
                    .run {
                        when (this) {
                            /* success */
                            is Result.Success -> {
                                LoadResult.Page(
                                    data = value,
                                    /* no previous pagination int as page */
                                    prevKey = _page.takeIf { it > 1 }?.dec(),
                                    /* no pagination if no results found else next page as +1 */
                                    nextKey = _page.takeIf { it <= 4 }?.inc()
                                )
                            }
                            /* error */
                            is Error -> LoadResult.Error(this)
                            else -> LoadResult.Error(IllegalStateException("$this type of [Result] is not allowed here"))
                        }
                    }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
}