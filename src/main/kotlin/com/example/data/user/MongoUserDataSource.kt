package com.example.data.user

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull

class MongoUserDataSource(
    db : MongoDatabase
) : UserDataSource {
    private val users = db.getCollection<User>("users")

    override suspend fun getUserById(username: String): User? {
        return users
            .find(eq(User::username, username))
            .firstOrNull()
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}