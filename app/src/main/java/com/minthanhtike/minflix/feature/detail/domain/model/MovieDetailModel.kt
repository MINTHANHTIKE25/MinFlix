package com.minthanhtike.minflix.feature.detail.domain.model

data class MovieDetailModel(
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val productionCompany: String,
    val movieImagesModel: List<MovieImagesModel>
):HomeDetailModel() {
    data class Genre(
        val id: Int,
        val name: String
    )
}
