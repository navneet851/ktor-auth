package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.event.*

fun Application.configureDatabases() {
    val mongoPass = System.getenv("MONGO_PASS")
    val connectionString = "mongodb+srv://nav700neet:$mongoPass@cluster0.61nhg.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val dbName = "ktor-auth"

    runBlocking {
        val database = connectToDatabase(connectionString, dbName)
    }
}

suspend fun connectToDatabase(connectionString: String, dbName: String): MongoDatabase = withContext(Dispatchers.IO) {
    val client = MongoClients.create(connectionString)
    client.getDatabase(dbName)
}