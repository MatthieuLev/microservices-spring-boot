package com.mleveque.imageservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
// Enable eureka client. It inherits from @EnableDiscoveryClient.
@EnableEurekaClient
class ImageServiceApplication

fun main(args: Array<String>) {
    runApplication<ImageServiceApplication>(*args)
}
