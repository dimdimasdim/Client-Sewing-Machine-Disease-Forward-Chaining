package com.dimas.networkexercise.base

object UserSetting {
    private lateinit var fullName: String
    private lateinit var npm: String

    fun storeUser(npm: String, fullName: String) {
        this.fullName = fullName
        this.npm = npm
    }

    override fun toString(): String {
        return "$fullName ($npm)"
    }
}