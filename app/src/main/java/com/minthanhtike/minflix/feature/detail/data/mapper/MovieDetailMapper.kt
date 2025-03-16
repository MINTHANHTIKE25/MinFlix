package com.minthanhtike.minflix.feature.detail.data.mapper

import com.minthanhtike.minflix.BuildConfig
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieDetailResponse
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel

fun MovieDetailResponse.toDomain(): HomeDetailModel {
    return HomeDetailModel(
        title = this.title.orEmpty(),
        rating = this.voteAverage ?: -1.0,
        companyName = this.productionCompanies?.filterNotNull()?.map {
            it.name.orEmpty()
        }.orEmpty().first(),
        backdropImg = if (backdropPath != null)
            BuildConfig.IMG_URL_ORIGINAL + this.backdropPath else "",
        overView = this.overview.orEmpty(),
        imagesList = this.movieImagesResponse?.backdrops?.filterNotNull()?.map {
            if (it.filePath != null) BuildConfig.IMG_URL_W780 + it.filePath else ""
        } ?: emptyList(),
        releaseDate = this.releaseDate,
        spokenLang = this.spokenLang?.map { it?.name.orEmpty() }.orEmpty(),
        productionCompany = this.productionCompanies?.map { it?.name.orEmpty() },
        budget = if (this.budget!= null) "${(this.budget/1000000).toDouble()} Million" else null,
        homePage = this.homepage
    )
}