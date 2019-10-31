package com.mleveque.galleryservice.controllers

import com.mleveque.galleryservice.entities.Gallery
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.core.env.Environment


@RestController
@RequestMapping("/")
class HomeController(@field:Autowired val restTemplate: RestTemplate, @field:Autowired val env: Environment) {

    @RequestMapping("/")
    fun home(): String {
        // This is useful for debugging
        // When having multiple instance of gallery service running at different ports.
        // We load balance among them, and display which instance received the request.
        return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port")
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/{id}")
    fun getGallery(@PathVariable id: Int): Gallery {
        LOGGER.info("Creating gallery object ... ")

        // create gallery object
        val gallery = Gallery()
        gallery.id = id

        // get list of available images
        // @SuppressWarnings("unchecked")    // we'll throw an exception from image service to simulate a failure
        val images = restTemplate.getForObject("http://image-service/images/", List::class.java)
        gallery.images = images

        return gallery
    }

    // -------- Admin Area --------
    // This method should only be accessed by users with role of 'admin'
    // We'll add the logic of role based auth later
    @RequestMapping("/admin")
    fun homeAdmin(): String {
        return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port")
    }

    // a fallback method to be called if failure happened
    fun fallback(galleryId: Int, hystrixCommand: Throwable): Gallery {
        return Gallery(galleryId)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(HomeController::class.java)
    }
}