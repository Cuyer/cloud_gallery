package pl.zajecia.cloud.cloudgallery

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform