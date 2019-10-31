package com.microservices.auth.security

import org.springframework.beans.factory.annotation.Value

class JwtConfig(
    @field:Value("\${security.jwt.uri:/auth/**}") val uri: String = "",
    @field:Value("\${security.jwt.header:Authorization}") val header: String = "",
    @field:Value("\${security.jwt.prefix:Bearer }") val prefix: String = "",
    @field:Value("\${security.jwt.expiration:#{24*60*60}}") val expiration: Int = 0,
    @field:Value("\${security.jwt.secret:JwtSecretKey}") val secret: String = ""
)