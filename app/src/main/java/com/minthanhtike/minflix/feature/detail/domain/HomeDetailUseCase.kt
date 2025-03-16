package com.minthanhtike.minflix.feature.detail.domain

import com.minthanhtike.minflix.common.filterBySize
import com.minthanhtike.minflix.feature.detail.data.repo.HomeDetailRepo
import javax.inject.Inject

class HomeDetailUseCase @Inject constructor(
    private val homeDetailRepo: HomeDetailRepo
) {
    suspend fun getMovieDetail(movieId: Int) = homeDetailRepo.getMovieDetail(movieId)
        .map { data ->
            val result = data.imagesList.filter { img -> img.isNotEmpty() }
                .filterBySize(10)
            data.copy(imagesList = result)
        }

    suspend fun getTvDetail(seriesId: Int) = homeDetailRepo.getTvDetail(seriesId)
        .map { data ->
            val result = data.imagesList.filter { img -> img.isNotEmpty() }
                .filterBySize(10)
            data.copy(imagesList = result)
        }

    suspend fun getMovieReview(movieId: Int) = homeDetailRepo.getMovieReviews(movieId)

    suspend fun getMovieRecommend(movieId: Int) = homeDetailRepo.getMovieRecommendation(movieId)

    suspend fun getMovieCasts(movieId: Int) = homeDetailRepo.getCasterCrew(movieId).map {
        it.copy(
            cast = it.cast.distinctBy { cast -> cast.id },
            crew = it.crew.distinctBy { crew -> crew.id }
        )
    }

    suspend fun getTrailers(id: Int, seasonNo: Int, type: String) =
        homeDetailRepo.getTrailers(id = id, seasonNo = seasonNo, type = type)
}
