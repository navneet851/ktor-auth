package com.example.data.user

interface UserDataSource {
    suspend fun getUserById(username: String): User?
    suspend fun insertUser(user: User): Boolean
}