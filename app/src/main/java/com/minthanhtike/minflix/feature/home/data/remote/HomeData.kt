package com.minthanhtike.minflix.feature.home.data.remote

import com.minthanhtike.minflix.common.KtorUtil
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getTrendingMovie(){
        httpClient
    }
}