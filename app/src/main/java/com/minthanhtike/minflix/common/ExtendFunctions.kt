package com.minthanhtike.minflix.common

import androidx.paging.PagingData

fun <T> List<T>.filterBySize(size: Int) = if (this.size > size) this.take(size) else this

fun <T : Any> PagingData<T>.filterDuplicateId(id:Int): Boolean {
    val seenIds = mutableListOf<Int>()
    return if (seenIds.contains(id)) {
            false // Filter out duplicates
        } else {
            seenIds.add(id)
            true
        }
}
