package com.dimas.networkexercise.utils

object UserSession {
    private lateinit var name: String
    private lateinit var npm: String

    fun setUser(name: String, npm: String) {
        this.name = name
        this.npm = npm
    }

    fun getName(): String {
        return  name
    }

    fun getFullUser(): String {
        return "$name($npm)"
    }
}