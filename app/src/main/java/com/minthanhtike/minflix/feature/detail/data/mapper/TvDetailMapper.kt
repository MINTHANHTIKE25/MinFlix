package com.minthanhtike.minflix.feature.detail.data.mapper

import com.minthanhtike.minflix.feature.detail.data.model.tvshow.TvDetailResponse
import com.minthanhtike.minflix.feature.detail.data.model.tvshow.TvImagesResponse
import com.minthanhtike.minflix.feature.detail.domain.model.TvDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.TvImagesModel

fun TvDetailResponse.toDomain(): TvDetailModel {
    return TvDetailModel(
        adult = this.adult ?: false,
        backdropPath = if (this.backdropPath != null)
            "https://image.tmdb.org/t/p/original${this.backdropPath}" else "",
        createdBy = this.createdBy?.filterNotNull()?.map {
            TvDetailModel.CreatedBy(
                creditId = it.creditId.orEmpty(),
                gender = it.gender ?: -1,
                id = it.id ?: -1,
                name = it.name.orEmpty(),
                originalName = it.originalName.orEmpty(),
                profilePath = it.profilePath.orEmpty()
            )
        }.orEmpty(),
        firstAirDate = this.firstAirDate.orEmpty(),
        lastAirDate = this.lastAirDate.orEmpty(),
        genres = this.genres?.filterNotNull()?.map {
            TvDetailModel.Genre(
                id = it.id!!,
                name = it.name.orEmpty()
            )
        }.orEmpty(),
        homepage = this.homepage.orEmpty(),
        id = this.id!!,
        inProduction = this.inProduction ?: false,
        name = this.name.orEmpty(),
        numberOfEpisodes = this.numberOfEpisodes ?: -1,
        numberOfSeasons = this.numberOfSeasons ?: -1,
        originalLanguage = this.originalLanguage.orEmpty(),
        originalName = this.originalName.orEmpty(),
        overview = this.overview.orEmpty(),
        popularity = this.popularity ?: -1.0,
        posterPath = this.posterPath.orEmpty(),
        productionCompany = this.productionCompanies?.let { company ->
            if (company.isNotEmpty()) {
                company.filterNotNull().map { it.name.orEmpty() }
                    .filter { it.isNotEmpty() }.take(1)[0]
            } else {
                ""
            }
        } ?: "",
        seasons = this.seasons?.filterNotNull()?.map {
            TvDetailModel.Season(
                airDate = it.airDate.orEmpty(),
                episodeCount = it.episodeCount ?: -1,
                id = it.id ?: -1,
                name = it.name.orEmpty(),
                overview = it.overview.orEmpty(),
                posterPath = it.posterPath.orEmpty(),
                seasonNumber = it.seasonNumber ?: -1,
                voteAverage = it.voteAverage ?: -1.0
            )
        }.orEmpty(),
        status = this.status.orEmpty(),
        tagline = this.tagline.orEmpty(),
        type = this.type.orEmpty(),
        voteAverage = this.voteAverage ?: -1.0,
        voteCount = this.voteCount ?: -1,
        tvImagesModel = this.tvImagesResponse?.toDomain().orEmpty()
    )
}

fun TvImagesResponse.toDomain(): List<TvImagesModel> {
    return this.backdrops?.filterNotNull()?.map { image ->
        TvImagesModel(
            aspectRatio = image.aspectRatio!!,
            filePath = if (image.filePath != null)
                "https://image.tmdb.org/t/p/w780${image.filePath}" else "",
            height = image.height ?: -1,
            width = image.width ?: -1,
            voteAverage = image.voteAverage ?: -1.0,
            voteCount = image.voteCount ?: -1,
            iso6391 = image.iso6391.orEmpty()
        )
    }.orEmpty()
}