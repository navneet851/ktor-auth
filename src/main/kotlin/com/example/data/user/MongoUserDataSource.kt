package com.example.data.user

import com.example.data.responses.UserResponse
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.util.logging.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import kotlin.math.log

class MongoUserDataSource(
    db : MongoDatabase
) : UserDataSource {
    private val users = db.getCollection<User>("users")

    override suspend fun getUserById(username: String): UserResponse? {
        val user = users.find(Filters.eq("username", username)).firstOrNull()
        return user?.let {
            UserResponse(it.username, it.password, it.salt, it.id.toString())
        }
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}