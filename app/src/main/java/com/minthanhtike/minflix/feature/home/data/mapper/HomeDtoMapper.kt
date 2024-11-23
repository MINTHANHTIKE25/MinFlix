package com.minthanhtike.minflix.feature.home.data.mapper

import com.minthanhtike.minflix.feature.home.data.model.NowPlayingMovieResponse
import com.minthanhtike.minflix.feature.home.data.model.TrendingMoviesResponse
import com.minthanhtike.minflix.feature.home.data.model.TrendingTvResponse
import com.minthanhtike.minflix.feature.home.domain.model.NowPlayMovieModel
import com.minthanhtike.minflix.feature.home.domain.model.TrendingMovieModels
import com.minthanhtike.minflix.feature.home.domain.model.TrendingTvModels
import io.ktor.utils.io.bits.reverseByteOrder

fun TrendingMoviesResponse.toDomain(): List<TrendingMovieModels> {
    return this.results.map { movies ->
        TrendingMovieModels(
            adult = movies.adult ?: false,
            backdropPath = if (movies.backdropPath != null)
                "http://image.tmdb.org/t/p/w500${movies.backdropPath}" else "",
            genreIds = movies.genreIds?.map {
                it ?: 0
            }.orEmpty(),
            id = movies.id!!,
            mediaType = movies.mediaType.orEmpty(),
            originalLanguage = movies.originalLanguage.orEmpty(),
            originalTitle = movies.originalTitle.orEmpty(),
            overview = movies.overview.orEmpty(),
            popularity = movies.popularity ?: 0.0,
            posterPath = if (movies.posterPath != null)
                "http://image.tmdb.org/t/p/w500${movies.posterPath}" else "",
            releaseDate = movies.releaseDate.orEmpty(),
            title = movies.title.orEmpty(),
            video = movies.video ?: false,
            voteAverage = movies.voteAverage ?: 0.0,
            voteCount = movies.voteCount ?: 0
        )
    }
}

fun TrendingTvResponse.toDomain(): List<TrendingTvModels> {
    return this.results.map { tv ->
        TrendingTvModels(
            adult = tv.adult ?: false,
            backdropPath = if (tv.backdropPath != null)
                "http://image.tmdb.org/t/p/w500${tv.backdropPath}" else "",
            genreIds = tv.genreIds?.map {
                it ?: 0
            }.orEmpty(),
            id = tv.id!!,
            mediaType = tv.mediaType.orEmpty(),
            originalLanguage = tv.originalLanguage.orEmpty(),
            overview = tv.overview.orEmpty(),
            popularity = tv.popularity ?: 0.0,
            posterPath = if (tv.posterPath != null)
                "http://image.tmdb.org/t/p/w500${tv.posterPath}" else "",
            voteAverage = tv.voteAverage ?: 0.0,
            firstAirDate = tv.firstAirDate.orEmpty(),
            name = tv.name.orEmpty(),
            originalName = tv.originalName.orEmpty(),
            originCountry = tv.originCountry?.map { it.orEmpty() }.orEmpty(),
            voteCount = tv.voteCount ?: 0
        )
    }
}

fun NowPlayingMovieResponse.toDomain(): List<NowPlayMovieModel> {
    return this.nowPlayingMovieDto?.map { movie ->
        NowPlayMovieModel(
            adult = movie.adult ?: false,
            backdropPath =  if (movie.backdropPath != null)
                "http://image.tmdb.org/t/p/w500${movie.backdropPath}" else "",
            genreIds = movie.genreIds?.map { it ?: -1 }.orEmpty(),
            id = movie.id!!,
            originalLanguage = movie.originalLanguage.orEmpty(),
            originalTitle = movie.originalTitle.orEmpty(),
            overview = movie.overview.orEmpty(),
            popularity = movie.popularity ?: 0.0,
            posterPath =  if (movie.posterPath != null)
                "http://image.tmdb.org/t/p/w500${movie.posterPath}" else "",
            releaseDate =movie.releaseDate.orEmpty(),
            title = movie.title.orEmpty(),
            video = movie.video ?: false,
            voteAverage = movie.voteAverage ?: 0.0,
            voteCount = movie.voteCount ?: 0
        )
    }.orEmpty()


}