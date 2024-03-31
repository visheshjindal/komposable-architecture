package com.toggl.komposable.sample.digibank.data

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val first: String,
    val last: String,
    val email: String,
    val phone: String,
    val address: String,
    val profilePicture: String,
    val currency: String
)