package com.minthanhtike.minflix.feature.detail.domain

import com.minthanhtike.minflix.feature.detail.data.repo.HomeDetailRepo
import javax.inject.Inject

class HomeDetailUseCase @Inject constructor(
    private val homeDetailRepo: HomeDetailRepo
) {
    suspend fun getMovieDetail(movieId: Int) = homeDetailRepo.getMovieDetail(movieId)
        .map {
            val result = if(it.movieImagesModel.size > 10){
                it.movieImagesModel.filter { image -> image.filePath.isNotEmpty() }.take(10)
            } else {
                it.movieImagesModel.filter { image -> image.filePath.isNotEmpty() }
            }
            it.copy(
                movieImagesModel = result
            )
        }

//    suspend fun getMovieImage(movieId: Int): Result<List<MovieImagesModel>> {
//        return homeDetailRepo.getMovieImages(movieId).map {
//            val result =if (it.size > 10) {
//                it.filter { image -> image.filePath.isNotEmpty() }.take(10)
//            } else {
//                it.filter { image -> image.filePath.isNotEmpty() }
//            }
//            result
//        }
//    }

    suspend fun getTvDetail(seriesId: Int) = homeDetailRepo.getTvDetail(seriesId).map {
        val result =if (it.tvImagesModel.size > 10) {
            it.tvImagesModel.filter { image -> image.filePath.isNotEmpty() }.take(10)
        } else {
            it.tvImagesModel.filter { image -> image.filePath.isNotEmpty() }
        }
        it.copy(tvImagesModel = result)
    }

//    suspend fun getTvImages(seriesId: Int): Result<List<TvImagesModel>> {
//         return homeDetailRepo.getTvImages(seriesId).map {
//             val result =if (it.size > 10) {
//                 it.filter { image -> image.filePath.isNotEmpty() }.take(10)
//             } else {
//                 it.filter { image -> image.filePath.isNotEmpty() }
//             }
//             result
//         }
//    }
}