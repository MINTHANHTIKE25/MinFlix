package com.minthanhtike.minflix.feature.detail.domain.model

data class TvDetailModel(
    val adult: Boolean,
    val backdropPath: String,
    val createdBy: List<CreatedBy>,
    val firstAirDate: String,
    val lastAirDate:String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val inProduction: Boolean,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompany: String,
    val seasons: List<Season>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,
    val tvImagesModel: List<TvImagesModel>
):HomeDetailModel(){
    data class CreatedBy(
        val creditId: String,
        val gender: Int,
        val id: Int,
        val name: String,
        val originalName: String,
        val profilePath: String
    )

    data class Genre(
        val id: Int,
        val name: String
    )

    data class Season(
        val airDate: String,
        val episodeCount: Int,
        val id: Int,
        val name: String,
        val overview: String,
        val posterPath: String,
        val seasonNumber: Int,
        val voteAverage: Double
    )
}
