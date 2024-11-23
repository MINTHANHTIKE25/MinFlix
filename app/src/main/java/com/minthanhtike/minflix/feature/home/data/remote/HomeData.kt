package com.minthanhtike.minflix.feature.home.data.remote

import android.util.Log
import com.minthanhtike.minflix.common.handle
import com.minthanhtike.minflix.feature.home.data.mapper.toDomain
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

    suspend fun getTrendingTv(time: String, page: Int): Result<List<TrendingTvModels>> {
        Log.wtf("pageTest","$page")
        return try {
            val result = httpClient.get {
                url(urlString = "3/trending/tv/$time?page=$page")
            }.body<TrendingTvResponse>().toDomain()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getNowPlayingMovies(page: Int)= handle<NowPlayingMovieResponse> {
        httpClient.get {
            url(urlString = "3/movie/now_playing")
            parameter("page","$page")
        }
    }.map { it.toDomain() }


}

sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data object Loading : Result<Nothing>
    data class Error(val throwable: Throwable) : Result<Nothing>
}
