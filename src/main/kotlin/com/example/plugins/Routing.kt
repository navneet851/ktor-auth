package com.example.plugins

import com.example.data.requests.AuthRequest
import com.example.data.responses.AuthResponse
import com.example.data.user.User
import com.example.data.user.UserDataSource
import com.example.security.hashing.HashService
import com.example.security.hashing.SHA256HashingService
import com.example.security.hashing.SaltedHash
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("signup") {
            val request = call.receive<AuthRequest>()
            val blankFields = request.username.isBlank() || request.password.isBlank()
            val passLength = request.password.length < 8
            if(blankFields || passLength){
                call.respond(HttpStatusCode.Conflict)
                return@post
            }
            val saltedHash = hashingService.generateSaltedHash(request.password)
            val user = User(
                username = request.username,
                password = saltedHash.hash,
                salt = saltedHash.salt
            )

            val wasAcknowledged = userDataSource.insertUser(user)
            if (!wasAcknowledged){
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)
        }
        post("signin") {
            val request = call.receive<AuthRequest>()

            val user = userDataSource.getUserById(request.username)
            if (user == null){
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            val isValidPass = hashingService.verify(
                value = request.password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )
            if (!isValidPass){
                call.respond(HttpStatusCode.Conflict)
                return@post
            }
            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.id.toString()
                )
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(
                    token = token
                )
            )

        }

        authentication {
            get("authenticate") {
                call.respond(HttpStatusCode.OK)
            }
        }

        authentication {
            get("secret") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim("userId", String::class)
                call.respond(HttpStatusCode.OK, "Your userId is $userId")
            }
        }
    }
}
