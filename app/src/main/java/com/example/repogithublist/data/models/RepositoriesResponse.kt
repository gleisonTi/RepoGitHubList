package com.example.repogithublist.data.models

data class RepositoriesResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)