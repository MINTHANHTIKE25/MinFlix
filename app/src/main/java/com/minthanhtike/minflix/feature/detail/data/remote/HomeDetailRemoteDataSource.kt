package com.minthanhtike.minflix.feature.detail.data.remote

import com.minthanhtike.minflix.common.handle
import com.minthanhtike.minflix.feature.detail.data.mapper.toDomain
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieCasterCrewResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieDetailResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieRecommendResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieReviewResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieTrailerResponse
import com.minthanhtike.minflix.feature.detail.data.model.tvshow.TvDetailResponse
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class HomeDetailRemoteDataSource @Inject constructor(
    private val  httpClient: HttpClient
) {
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailResponse> {
        return handle<MovieDetailResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/movie/$movieId")
                parameter("append_to_response", "images")
                parameter("language", "null")
                parameter("include_image_language", "null")
            }
        }
    }

    suspend fun getMovieCaster(movieId: Int): Result<MovieCasterCrewResponse> {
        return handle<MovieCasterCrewResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/movie/$movieId/credits")
            }
        }
    }

    suspend fun getMovieReviews(movieId: Int, page: Int): Result<MovieReviewResponse> {
        return handle<MovieReviewResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/movie/$movieId/reviews")
                parameter("page", "$page")
            }
        }

    }

    suspend fun getRecommendMovies(movieId: Int, page: Int): Result<MovieRecommendResponse> {
        return handle<MovieRecommendResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/movie/${movieId}/recommendations")
                parameter("page", "$page")
            }
        }
    }

    suspend fun getMovieTrailers(movieId: Int): Result<MovieTrailerResponse> {
        return handle<MovieTrailerResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/movie/$movieId/videos")
            }
        }
    }


    suspend fun getTvDetails(seriesId: Int): Result<TvDetailResponse> {
        return handle<TvDetailResponse> {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/tv/$seriesId")
                parameter("append_to_response", "images")
                parameter("language", "null")
                parameter("include_image_language", "null")
            }
        }
    }

    suspend fun getTvTrailer(seriesId: Int,season:Int): Result<MovieTrailerResponse> {
        return handle {
            httpClient.get {
                url(urlString = "https://api.themoviedb.org/3/tv/$seriesId/videos")
            }
        }
    }

    suspend fun getTvEpisodes(seriesId: Int) {
        httpClient.get {
            url(urlString = "")
        }
    }

    suspend fun getTvCaster(seriesId: Int) {
        httpClient.get {

        }
    }

    suspend fun getTvReviews(seriesId: Int) {
        httpClient.get {

        }
    }

    suspend fun getRecommendTvs(seriesId: Int) {
        httpClient.get {

        }
    }


}
