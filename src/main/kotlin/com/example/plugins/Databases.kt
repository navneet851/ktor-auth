package com.example.plugins

import com.example.data.user.MongoUserDataSource
import com.example.data.user.User
import com.mongodb.client.*
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun Application.configureDatabases() {
    val mongoPass = System.getenv("MONGO_PASS")
    val connectionString = "mongodb+srv://nav700neet:$mongoPass@cluster0.61nhg.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val dbName = "ktor-auth"

    val client = MongoClient.create(connectionString)
    val db = client.getDatabase(dbName)
    val mongoUserDataSource = MongoUserDataSource(db)
    runBlocking {
        val user = User(
            username = "navbar",
            password = "navi",
            salt = "salt"
        )
        mongoUserDataSource.insertUser(user)
    }
}
