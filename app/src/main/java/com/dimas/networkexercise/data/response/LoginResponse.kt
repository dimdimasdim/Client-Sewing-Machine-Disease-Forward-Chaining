package com.dimas.networkexercise.data.response


import com.google.gson.annotations.SerializedName
import com.dimas.networkexercise.domain.model.User

data class LoginResponse(
    @SerializedName("menu")
    val menu: List<MenuResponse?>?,
    @SerializedName("role")
    val role: RoleResponse?,
    @SerializedName("token")
    val token: String?
) {
    data class MenuResponse(
        @SerializedName("menuName")
        val menuName: String?
    )

    data class RoleResponse(
        @SerializedName("roleName")
        val roleName: String?
    )
}

fun LoginResponse?.mapToUser(): User {
    return User(
        userName = this?.role?.roleName.orEmpty(),
        token = this?.token.orEmpty()
    )
}