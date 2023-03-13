package com.example.repogithublist.domain.model

data class Repository(
    val name: String,
    val author: String,
    val image: String,
    val stargazersCount: Int,
    val forksCount: Int
)
