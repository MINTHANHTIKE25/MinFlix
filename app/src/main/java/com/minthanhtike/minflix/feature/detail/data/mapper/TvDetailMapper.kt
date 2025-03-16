package com.minthanhtike.minflix.feature.detail.data.mapper

import com.minthanhtike.minflix.BuildConfig
import com.minthanhtike.minflix.feature.detail.data.model.tvshow.TvDetailResponse
import com.minthanhtike.minflix.feature.detail.domain.model.HomeDetailModel

fun TvDetailResponse.toDomain(): HomeDetailModel {
    return HomeDetailModel(
        title = this.name.orEmpty(),
        rating = this.voteAverage ?: -1.0,
        companyName = this.productionCompanies?.filterNotNull()?.map {
            it.name.orEmpty()
        }.orEmpty().first(),
        backdropImg = if (backdropPath != null)
            BuildConfig.IMG_URL_ORIGINAL + this.backdropPath else "",
        overView = this.overview.orEmpty(),
        imagesList = this.tvImagesResponse?.backdrops?.filterNotNull()?.map {
            if (it.filePath != null)
                BuildConfig.IMG_URL_W780 + it.filePath else ""
        } ?: emptyList(),
        releaseDate = this.firstAirDate,
        spokenLang = listOf(this.name.orEmpty()),
        productionCompany = this.productionCompanies?.map { it?.name.orEmpty() },
        budget = null,
        homePage = this.homepage
    )
}