package com.minthanhtike.minflix.di

import android.util.Log
import com.minthanhtike.minflix.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun createKtor(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url(urlString = BuildConfig.API_URL)
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${BuildConfig.API_KEY}"
                    )
                }
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                }
            }
            install(Logging){
                level = LogLevel.BODY
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("HttpClient", message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }
}