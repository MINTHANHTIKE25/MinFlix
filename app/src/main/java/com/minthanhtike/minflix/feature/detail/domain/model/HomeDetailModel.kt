package com.minthanhtike.minflix.feature.detail.domain.model

data class HomeDetailModel(
    val title:String,
    val rating:Double,
    val companyName:String,
    val backdropImg:String,
    val overView:String,
    val imagesList:List<String>,
    val releaseDate:String?,
    val spokenLang:List<String>?,
    val homePage:String?,
    val productionCompany:List<String>?,
    val budget:String?
)