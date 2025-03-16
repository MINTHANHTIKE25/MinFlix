package com.minthanhtike.minflix.common

import com.minthanhtike.minflix.feature.detail.ui.HomeDetailAction

enum class TabItems(
    val label:String,
    val action:HomeDetailAction
) {
    Episodes(label = "Episodes", action = HomeDetailAction.Episodes),
    About(label = "About" , action = HomeDetailAction.About),
    Reviews(label = "Reviews" , action = HomeDetailAction.Reviews),
    Trailers(label = "Trailers", action = HomeDetailAction.Trailers),
    Recommendation(label = "More like this" , action = HomeDetailAction.Recommendation),

}