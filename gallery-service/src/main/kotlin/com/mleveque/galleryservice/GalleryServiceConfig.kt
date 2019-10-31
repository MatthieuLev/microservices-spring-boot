package com.mleveque.galleryservice

import org.springframework.web.client.RestTemplate
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
internal class GalleryServiceConfig {

    // Create a bean for restTemplate to call services
    @Bean
    // Load balance between service instances running at different ports.
    @LoadBalanced
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}