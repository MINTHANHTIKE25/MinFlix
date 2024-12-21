package com.minthanhtike.minflix.feature.detail.data.remote

import com.minthanhtike.minflix.common.handle
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieDetailResponse
import com.minthanhtike.minflix.feature.detail.data.model.tvshow.TvDetailResponse
import com.minthanhtike.minflix.feature.detail.data.model.tvshow.TvImagesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class HomeDetailRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailResponse> {
        return handle<MovieDetailResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/movie/$movieId")
                parameter("append_to_response","images")
                parameter("language","null")
                parameter("include_image_language","null")
            }
        }
    }

//    suspend fun getMovieImages(movieId: Int): Result<MovieImagesResponse> {
//        return handle<MovieImagesResponse> {
//            httpClient.get {
//                url(urlString = "https://api.themoviedb.org/3/movie/$movieId/images")
//            }
//        }
//    }

    suspend fun getMovieCaster(movieId: Int) {
        httpClient.get {
            url(urlString = "https://api.themoviedb.org/3/movie/$movieId/credits")
        }
    }

    suspend fun getMovieTrailer(movieId: Int) {
        httpClient.get {
            url(urlString = "https://api.themoviedb.org/3/movie/$movieId/videos")
        }
    }

    suspend fun getMovieReviews(movieId: Int) {
        httpClient.get {
            url(urlString = "https://api.themoviedb.org/3/movie/$movieId/reviews")
        }
    }

    suspend fun getRecommendMovies(movieId: Int) {
        httpClient.get {
            url(urlString = "https://api.themoviedb.org/3/movie/${movieId}/recommendations")
        }
    }


    suspend fun getTvImages(seriesId: Int): Result<TvImagesResponse> {
        return handle<TvImagesResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/tv/$seriesId/images")
                parameter("include_image_language", "null")
                parameter("language", "null")
            }
        }
    }


    suspend fun getTvDetails(seriesId: Int): Result<TvDetailResponse> {
        return handle<TvDetailResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/tv/$seriesId")
                parameter("append_to_response","images")
                parameter("language","null")
                parameter("include_image_language","null")
            }
        }
    }

    suspend fun getTvTrailer(seriesId: Int) {
        httpClient.get {
            url(urlString = "https://api.themoviedb.org/3/tv/$seriesId/videos")
        }
    }

    suspend fun getTvEpisodes(seriesId: Int){
        httpClient.get {
            url(urlString = "")
        }
    }

    suspend fun getTvCaster(seriesId: Int){
        httpClient.get {

        }
    }

    suspend fun getTvReviews(seriesId: Int){
        httpClient.get {

        }
    }

    suspend fun getRecommendTvs(seriesId: Int){
        httpClient.get {

        }
    }

    
}
