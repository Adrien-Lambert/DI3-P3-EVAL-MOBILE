package com.example.evalp3.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

@Serializable
data class PaginatedResponse<T>(
    val info: PaginatedInfo,
    val results: List<T>
)
