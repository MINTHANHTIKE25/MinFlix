package com.minthanhtike.minflix.feature.home.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.home.data.mapper.toDomain
import com.minthanhtike.minflix.feature.home.data.paginator.AiringTvTodayPagingSource
import com.minthanhtike.minflix.feature.home.data.paginator.NowPlayMoviePagingSource
import com.minthanhtike.minflix.feature.home.data.paginator.TrendingTvPagingSource
import com.minthanhtike.minflix.feature.home.data.remote.HomeRemoteDataSource
import com.minthanhtike.minflix.feature.home.domain.model.AiringTvTodayModel
import com.minthanhtike.minflix.feature.home.domain.model.NowPlayMovieModel
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepo {
    override suspend fun getTrendingMovie(time: String): Result<List<TrendingMovieModels>> {
        return withContext(Dispatchers.IO) {
            homeRemoteDataSource.getTrendingMovie(time)
        }
    }

    override suspend fun getTrendingTv(time: String): Flow<PagingData<TrendingTvModels>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    TrendingTvPagingSource(homeRemoteDataSource, time)
                }
            ).flow
        }
    }

    override suspend fun getNowPlayingMovie(): Flow<PagingData<NowPlayMovieModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    NowPlayMoviePagingSource(homeRemoteDataSource)
                }
            ).flow
        }
    }

    override suspend fun getAirTvToday(): Flow<PagingData<AiringTvTodayModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    AiringTvTodayPagingSource(homeRemoteDataSource)
                }
            ).flow
        }
    }


}