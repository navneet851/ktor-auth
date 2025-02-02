package com.example

import com.example.data.user.MongoUserDataSource
import com.example.plugins.configureDatabases
import com.example.plugins.configureSerialization
import com.example.plugins.configureMonitoring
import com.example.plugins.configureSecurity
import com.example.plugins.configureRouting
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenConfig
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {

    val mongoPass = "navbarneet"
    val navSecret = "navsecret"
    val connectionString = "mongodb+srv://nav700neet:$mongoPass@cluster0.61nhg.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&ssl=false"
    val dbName = "ktor-auth"

    val client = MongoClient.create(connectionString)
    val db = client.getDatabase(dbName)

    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = navSecret


    )

    val userDataSource = MongoUserDataSource(db)
    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()

    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(hashingService, userDataSource, tokenService, tokenConfig)
}
