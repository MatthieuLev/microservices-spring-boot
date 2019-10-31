package com.mleveque.zuul

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootApplication
@EnableEurekaClient        // It acts as a eureka client
@EnableZuulProxy        // Enable Zuul
class ZuulApplication

fun main(args: Array<String>) {
    runApplication<ZuulApplication>(*args)
}
