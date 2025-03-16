package com.minthanhtike.minflix.feature.detail.data.repo

import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailTrailerModel
import kotlinx.coroutines.flow.Flow

interface HomeDetailRepo {

    suspend fun getMovieDetail(movieId: Int): Result<HomeDetailModel>

    suspend fun getCasterCrew(movieId: Int): Result<HomeDetailCasterCrewModel>

    suspend fun getMovieRecommendation(movieId: Int): Flow<PagingData<HomeDetailRecommendModel>>

    suspend fun getMovieReviews(movieId: Int):Flow<PagingData<HomeDetailReviewModel>>

    suspend fun getTrailers(id: Int, type:String, seasonNo:Int):Result<HomeDetailTrailerModel>

    suspend fun getTvDetail(tvId: Int): Result<HomeDetailModel>

}