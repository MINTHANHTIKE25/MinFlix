package com.minthanhtike.minflix.feature.detail.data.mapper

import com.minthanhtike.minflix.BuildConfig
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieCasterCrewResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieRecommendResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieReviewResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieTrailerResponse
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailCasterCrewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailRecommendModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailReviewModel
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailTrailerModel

fun MovieCasterCrewResponse.toDomain(): HomeDetailCasterCrewModel {
    return HomeDetailCasterCrewModel(
        crew = this.crew?.filterNotNull()?.map { cast ->
            HomeDetailCasterCrewModel.Crew(
                creditId = cast.creditId!!,
                department = cast.knownForDepartment.orEmpty(),
                gender = cast.gender ?: -1,
                id = cast.id!!,
                job = cast.job.orEmpty(),
                knownForDepartment = cast.knownForDepartment.orEmpty(),
                name = cast.name.orEmpty(),
                originalName = cast.originalName.orEmpty(),
                popularity = cast.popularity ?: -1.0,
                profilePath = if (cast.profilePath == null) "" else
                    BuildConfig.IMG_URL_W780 + cast.profilePath
            )
        }.orEmpty(),
        cast = this.cast?.filterNotNull()?.map { cast ->
            HomeDetailCasterCrewModel.Cast(
                castId = cast.castId!!,
                character = cast.character.orEmpty(),
                creditId = cast.creditId!!,
                gender = cast.gender ?: -1,
                id = cast.id!!,
                knownForDepartment = cast.knownForDepartment.orEmpty(),
                name = cast.name.orEmpty(),
                originalName = cast.originalName.orEmpty(),
                popularity = cast.popularity ?: -1.0,
                profilePath = if (cast.profilePath == null) "" else
                    BuildConfig.IMG_URL_W780 + cast.profilePath
            )
        }.orEmpty()
    )
}

fun MovieRecommendResponse.Result.toDomain(): HomeDetailRecommendModel {
    return HomeDetailRecommendModel(
        adult = this.adult ?: false,
        backdropPath = this.backdropPath.orEmpty(),
        genreIds = this.genreIds?.map {
            it ?: -1
        }.orEmpty(),
        id = this.id!!,
        mediaType = this.mediaType.orEmpty(),
        originalLanguage = this.originalLanguage.orEmpty(),
        originalTitle = this.originalTitle.orEmpty(),
        overview = this.overview.orEmpty(),
        popularity = this.popularity ?: -1.0,
        posterPath = this.posterPath.orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        title = this.title.orEmpty(),
        video = this.video ?: false,
        voteAverage = this.voteAverage ?: -1.0,
        voteCount = this.voteCount ?: -1
    )
}


fun MovieReviewResponse.Result.toDomain(): HomeDetailReviewModel {
    return HomeDetailReviewModel(
        author = this.author.orEmpty(),
        content = this.content.orEmpty(),
        createdAt = this.createdAt.orEmpty(),
        id = this.id!!,
        updatedAt = this.updatedAt.orEmpty(),
        url = this.url.orEmpty(),
        authorDetails = HomeDetailReviewModel.AuthorDetails(
            avatarPath = if (this.authorDetails?.avatarPath != null)
                BuildConfig.IMG_URL_W500 + this.authorDetails.avatarPath else "",
            name = this.authorDetails?.name.orEmpty(),
            rating = this.authorDetails?.rating ?: -1.0,
            username = this.authorDetails?.username.orEmpty()
        )
    )
}

fun MovieTrailerResponse.toDomain(): HomeDetailTrailerModel {
    return HomeDetailTrailerModel(
        results = this.results?.filterNotNull()?.map { trailer ->
            HomeDetailTrailerModel.Trailer(
                id = trailer.id!!,
                videoSrc = trailer.key.orEmpty(),
                name = trailer.name.orEmpty(),
                official = trailer.official ?: false,
                publishedAt = trailer.publishedAt.orEmpty(),
                site = trailer.site.orEmpty(),
                size = trailer.size ?: -1,
                type = trailer.type.orEmpty(),
                videoImg = if (trailer.key != null) {
                    "https://i.ytimg.com/vi/${trailer.key}/hqdefault.jpg"
                } else {
                    ""
                }
            )
        }.orEmpty()
    )
}