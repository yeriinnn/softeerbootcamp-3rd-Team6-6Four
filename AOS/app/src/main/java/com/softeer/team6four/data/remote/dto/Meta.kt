package com.softeer.team6four.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val count: Int? = null,
    val page: Int? = null,
    val totalCount: Int? = null
)