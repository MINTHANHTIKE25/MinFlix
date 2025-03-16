package com.minthanhtike.minflix.feature.detail.domain.model

data class HomeDetailTrailerModel(
    val results: List<Trailer>
):HomeDetailRelatedInfoModels {
    data class Trailer(
        val id: String,
        val videoSrc: String,
        val videoImg:String,
        val name: String,
        val official: Boolean,
        val publishedAt: String,
        val site: String,
        val size: Int,
        val type: String
    )
}