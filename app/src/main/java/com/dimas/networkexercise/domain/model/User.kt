package com.dimas.networkexercise.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userName: String? = null,
    val token: String? = null
)