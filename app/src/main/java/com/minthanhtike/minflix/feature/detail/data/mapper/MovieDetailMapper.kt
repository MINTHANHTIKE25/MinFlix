package com.minthanhtike.minflix.feature.detail.data.mapper

import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieDetailResponse
import com.minthanhtike.minflix.feature.detail.data.model.movie.MovieImagesResponse
import com.minthanhtike.minflix.feature.detail.domain.model.MovieDetailModel
import com.minthanhtike.minflix.feature.detail.domain.model.MovieImagesModel

fun MovieDetailResponse.toDomain(): MovieDetailModel {
    return MovieDetailModel(
        adult = this.adult ?: false,
        backdropPath = if (this.backdropPath != null)
            "https://image.tmdb.org/t/p/original${this.backdropPath}" else "",
        budget = this.budget ?: -1,
        genres = this.genres?.filterNotNull()?.map { genre ->
            MovieDetailModel.Genre(
                id = genre.id ?: -1,
                name = genre.name.orEmpty()
            )
        }.orEmpty(),
        homepage = this.homepage.orEmpty(),
        id = this.id!!,
        originCountry = this.originCountry?.map { it.orEmpty() }.orEmpty(),
        originalLanguage = this.originalLanguage.orEmpty(),
        originalTitle = this.originalTitle.orEmpty(),
        overview = this.overview.orEmpty(),
        popularity = this.popularity ?: 0.0,
        posterPath = this.posterPath.orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        revenue = this.revenue ?: 0,
        runtime = this.runtime ?: 0,
        status = this.status.orEmpty(),
        tagline = this.tagline.orEmpty(),
        title = this.title.orEmpty(),
        video = this.video ?: false,
        voteAverage = this.voteAverage ?: -1.0,
        voteCount = this.voteCount ?: -1,
        productionCompany = this.productionCompanies?.let { company ->
            if (company.isNotEmpty()) {
                company.filterNotNull().map { it.name.orEmpty() }
                    .filter { it.isNotEmpty() }.take(1)[0]
            } else {
                ""
            }
        } ?: "",
        movieImagesModel = this.movieImagesResponse?.toDomain().orEmpty()
    )
}

fun MovieImagesResponse.toDomain(): List<MovieImagesModel> {
    return this.backdrops?.filterNotNull()?.map { image ->
        MovieImagesModel(
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