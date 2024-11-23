package com.minthanhtike.minflix.feature.home.data.repo

import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.home.domain.model.AiringTvTodayModel
import com.minthanhtike.minflix.feature.home.domain.model.NowPlayMovieModel
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun getTrendingMovie(time: String): Result<List<TrendingMovieModels>>

    suspend fun getTrendingTv(time: String): Flow<PagingData<TrendingTvModels>>

    suspend fun getNowPlayingMovie() : Flow<PagingData<NowPlayMovieModel>>

    suspend fun getAirTvToday() : Flow<PagingData<AiringTvTodayModel>>
}