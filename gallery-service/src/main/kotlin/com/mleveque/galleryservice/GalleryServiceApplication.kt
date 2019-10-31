package com.mleveque.galleryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
// Enable circuit breakers
@EnableCircuitBreaker
// Enable eureka client.
@EnableEurekaClient
class GalleryServiceApplication

fun main(args: Array<String>) {
    runApplication<GalleryServiceApplication>(*args)
}