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

//    runBlocking {
//        val user = User(
//            username = "navbar",
//            password = "navi",
//            salt = "salt"
//        )
//        mongoUserDataSource.insertUser(user)
//    }
}
