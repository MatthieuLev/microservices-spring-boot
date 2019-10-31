package com.microservices.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}
