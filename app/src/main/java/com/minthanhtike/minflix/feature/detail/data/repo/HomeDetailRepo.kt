package com.minthanhtike.minflix.feature.detail.data.repo

import com.minthanhtike.minflix.feature.detail.domain.model.MovieDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.MovieImagesModel
import com.minthanhtike.minflix.feature.detail.domain.model.TvDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.TvImagesModel

interface HomeDetailRepo {

    suspend fun getMovieDetail(movieId:Int):Result<MovieDetailModel>

//    suspend fun getMovieImages(movieId: Int):Result<List<MovieImagesModel>>

    suspend fun getTvDetail(tvId:Int):Result<TvDetailModel>

//    suspend fun getTvImages(seriesId:Int):Result<List<TvImagesModel>>
}