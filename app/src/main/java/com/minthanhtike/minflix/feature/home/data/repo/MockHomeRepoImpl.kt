package com.minthanhtike.minflix.feature.home.data.repo

import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.home.domain.model.AiringTvTodayModel
import com.minthanhtike.minflix.feature.home.domain.model.NowPlayMovieModel
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import com.minthanhtike.minflix.feature.home.ui.TrendingMovieState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MockHomeRepoImpl @Inject constructor() : HomeRepo {
    override suspend fun getTrendingMovie(time: String): Result<List<TrendingMovieModels>> {
        delay(1300)
        return Result.failure(Error())
    }

    override suspend fun getTrendingTv(time: String): Flow<PagingData<TrendingTvModels>> {
        return flow {
            PagingData.empty<TrendingTvModels>()
        }

    }

    override suspend fun getNowPlayingMovie(): Flow<PagingData<NowPlayMovieModel>> {
        return flow {
            PagingData.empty<TrendingTvModels>()
        }
    }

    override suspend fun getAirTvToday(): Flow<PagingData<AiringTvTodayModel>> {
        return flow {
            PagingData.empty<TrendingTvModels>()
        }
    }
}