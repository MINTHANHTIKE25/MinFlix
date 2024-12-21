package com.minthanhtike.minflix.feature.detail.data.repo

import com.minthanhtike.minflix.feature.detail.data.mapper.toDomain
import com.minthanhtike.minflix.feature.detail.data.remote.HomeDetailRemoteDataSource
import com.minthanhtike.minflix.feature.detail.domain.model.MovieDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.TvDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeDetailRepoImpl @Inject constructor(
    private val homeDetailRemoteDataSource: HomeDetailRemoteDataSource
) : HomeDetailRepo {
    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailModel> {
        return withContext(Dispatchers.IO) {
            homeDetailRemoteDataSource.getMovieDetails(movieId).map { it.toDomain() }
        }
    }

//    override suspend fun getMovieImages(movieId: Int): Result<List<MovieImagesModel>> {
//        return withContext(Dispatchers.IO) {
////            homeDetailRemoteDataSource.getMovieImages(movieId).map { it.toDomain() }
//        }
//
//    }

    override suspend fun getTvDetail(tvId: Int): Result<TvDetailModel> {
        return withContext(Dispatchers.IO) {
            homeDetailRemoteDataSource.getTvDetails(tvId).map { it.toDomain() }
        }
    }

//    override suspend fun getTvImages(seriesId: Int): Result<List<TvImagesModel>> {
//        return withContext(Dispatchers.IO){
//            homeDetailRemoteDataSource.getTvImages(seriesId).map { it.toDomain() }
//        }
//    }

}