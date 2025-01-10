package com.example.data.user

import com.example.data.responses.UserResponse

interface UserDataSource {
    suspend fun getUserById(username: String): UserResponse?
    suspend fun insertUser(user: User): Boolean
}