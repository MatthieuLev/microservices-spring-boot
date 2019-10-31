package com.mleveque.galleryservice.entities

class Gallery {
    var id: Int = 0
    var images: List<*>? = null

    constructor() {}

    constructor(galleryId: Int) {
        this.id = galleryId
    }
}
