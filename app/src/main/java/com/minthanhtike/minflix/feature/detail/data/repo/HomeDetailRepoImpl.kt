package com.minthanhtike.minflix.feature.detail.data.repo

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.minthanhtike.minflix.feature.detail.data.mapper.toDomain
import com.minthanhtike.minflix.feature.detail.data.pagingsource.RecommendationPagingSource
import com.minthanhtike.minflix.feature.detail.data.pagingsource.ReviewPagingSource
import com.minthanhtike.minflix.feature.detail.data.remote.HomeDetailRemoteDataSource
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailTrailerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeDetailRepoImpl @Inject constructor(
    private val homeDetailRemoteDataSource: HomeDetailRemoteDataSource
) : HomeDetailRepo {
    override suspend fun getMovieDetail(movieId: Int): Result<HomeDetailModel> {
        return withContext(Dispatchers.IO) {
            homeDetailRemoteDataSource.getMovieDetails(movieId).map { it.toDomain() }
        }
    }

    override suspend fun getCasterCrew(movieId: Int): Result<HomeDetailCasterCrewModel> {
        return withContext(Dispatchers.IO) {
            homeDetailRemoteDataSource.getMovieCaster(movieId).map { it.toDomain() }
        }
    }

    override suspend fun getMovieRecommendation(movieId: Int): Flow<PagingData<HomeDetailRecommendModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    RecommendationPagingSource(
                        homeDetailRemoteDataSource = homeDetailRemoteDataSource,
                        id = movieId
                    )
                }
            ).flow
        }
    }


    override suspend fun getMovieReviews(movieId: Int): Flow<PagingData<HomeDetailReviewModel>> {
        return withContext(Dispatchers.IO) {
            Pager(
                config = PagingConfig(pageSize = 5),
                pagingSourceFactory = {
                    ReviewPagingSource(
                        homeDetailRemoteDataSource = homeDetailRemoteDataSource,
                        id = movieId
                    )
                }
            ).flow
        }
    }

    override suspend fun getTrailers(id: Int,type: String,seasonNo:Int): Result<HomeDetailTrailerModel> {
        return withContext(Dispatchers.IO){

            if (type.contains("Tv", ignoreCase = true)){
                homeDetailRemoteDataSource.getTvTrailer(seriesId = id, season = seasonNo).map { it.toDomain() }
            }else{Log.wtf("datasFromOps","$id")
                homeDetailRemoteDataSource.getMovieTrailers(id).map { it.toDomain() }
            }

        }
    }


    override suspend fun getTvDetail(tvId: Int): Result<HomeDetailModel> {
        return withContext(Dispatchers.IO) {
            homeDetailRemoteDataSource.getTvDetails(tvId).map { it.toDomain() }
        }
    }


}