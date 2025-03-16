package com.minthanhtike.minflix.feature.detail.domain.model

data class HomeDetailReviewModel(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
):HomeDetailRelatedInfoModels {

    data class AuthorDetails(
        val avatarPath: String,
        val name: String,
        val rating: Double,
        val username: String
    )
}
