package com.minthanhtike.minflix.feature.home.data.remote

import android.util.Log
import com.minthanhtike.minflix.common.handle
import com.minthanhtike.minflix.feature.home.data.mapper.toDomain
import com.minthanhtike.minflix.feature.home.data.model.AiringTvTodayResponse
import com.minthanhtike.minflix.feature.home.data.model.NowPlayingMovieResponse
import com.minthanhtike.minflix.feature.home.data.model.TrendingMoviesResponse
import com.minthanhtike.minflix.feature.home.data.model.TrendingTvResponse
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getTrendingMovie(time: String) = handle<TrendingMoviesResponse> {
        httpClient.get {
            url(urlString = "3/trending/movie/$time")
            parameter("page", 1)
        }
    }.map { it.toDomain() }

    suspend fun getTrendingTv(time: String, page: Int) = handle<TrendingTvResponse> {
        httpClient.get {
            url(urlString = "3/trending/tv/$time")
            parameter("page", "$page")
        }
    }.map { it.toDomain() }

    suspend fun getNowPlayingMovies(page: Int) = handle<NowPlayingMovieResponse> {
        httpClient.get {
            url(urlString = "3/movie/now_playing")
            parameter("page", "$page")
        }
    }.map { it.toDomain() }

    suspend fun getAirTodayTv(page: Int) = handle<AiringTvTodayResponse> {
        httpClient.get {
            url(urlString = "3/tv/airing_today")
            parameter("page", "$page")
        }
    }.map { it.toDomain() }
}
