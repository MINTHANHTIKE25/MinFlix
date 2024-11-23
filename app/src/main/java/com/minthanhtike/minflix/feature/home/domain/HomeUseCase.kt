package com.minthanhtike.minflix.feature.home.domain

import com.minthanhtike.minflix.feature.home.data.repo.HomeRepo
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
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

    suspend fun getNowPlayMovies() = homeRepo.getNowPlayingMovie()

    suspend fun getAiringTvToday() = homeRepo.getAirTvToday()

}