package com.example.data.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class User(
    val username: String,
    val password: String,
    val salt: String,
    @BsonId val id: ObjectId = ObjectId()
)