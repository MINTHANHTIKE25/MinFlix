package com.minthanhtike.minflix.feature.home.domain

import androidx.collection.mutableIntListOf
import androidx.paging.filter
import androidx.paging.map
import com.minthanhtike.minflix.common.filterDuplicateId
import com.minthanhtike.minflix.feature.home.data.repo.HomeRepo
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val homeRepo: HomeRepo
) {
    suspend fun getTrendingMovies(time: String): Result<List<TrendingMovieModels>> {
        return homeRepo.getTrendingMovie(time)
            .map { trendMovies ->
                val result = mutableListOf<TrendingMovieModels>()
                if (time == "day") {
                    val dayList = trendMovies.filter { it.backdropPath.isNotEmpty() }.take(7)
                    result.addAll(dayList)
                } else {
                    val weekList = trendMovies.filter { it.backdropPath.isNotEmpty() }.takeLast(7)
                    result.addAll(weekList)
                }
                result
            }
    }

    suspend fun getTrendingTv(time: String) = homeRepo.getTrendingTv(time)
        .map { pagingData ->
            val seenIds = mutableListOf<Int>()
            pagingData.filter { item ->
                if (seenIds.contains(item.id)) {
                    false // Filter out duplicates
                } else {
                    seenIds.add(item.id)
                    true
                }
            }
        }

    suspend fun getNowPlayMovies() = homeRepo.getNowPlayingMovie()
        .map { pagingData ->
            val seenIdList = mutableIntListOf()
            pagingData.filter { item ->
                if (seenIdList.contains(item.id)) {
                    false // Filter out duplicates
                } else {
                    seenIdList.add(item.id)
                    true
                }
            }
        }

    suspend fun getAiringTvToday() = homeRepo.getAirTvToday()
        .map { pagingData ->
            val seenIdList = mutableIntListOf()
            pagingData.filter { item ->
                if (seenIdList.contains(item.id)) {
                    false // Filter out duplicates
                } else {
                    seenIdList.add(item.id)
                    true
                }
            }
        }

}

