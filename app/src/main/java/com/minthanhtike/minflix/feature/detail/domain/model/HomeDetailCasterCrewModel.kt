package com.minthanhtike.minflix.feature.detail.domain.model

data class HomeDetailCasterCrewModel(
    val cast: List<Cast>,
    val crew: List<Crew>
):HomeDetailRelatedInfoModels{
    data class Crew(
        val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        val knownForDepartment: String,
        val name: String,
        val originalName: String,
        val popularity: Double,
        val profilePath: String
    )

    data class Cast(
        val castId: Int,
        val character: String,
        val creditId: String,
        val gender: Int,
        val id: Int,
        val knownForDepartment: String,
        val name: String,
        val originalName: String,
        val popularity: Double,
        val profilePath: String
    )
}
