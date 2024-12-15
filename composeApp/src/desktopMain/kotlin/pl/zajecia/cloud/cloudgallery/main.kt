package pl.zajecia.cloud.cloudgallery

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import android.app.Application
import com.google.firebase.FirebasePlatform

fun main() = application {

    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
        val storage = mutableMapOf<String, String>()
        override fun store(key: String, value: String) = storage.set(key, value)
        override fun retrieve(key: String) = storage[key]
        override fun clear(key: String) { storage.remove(key) }
        override fun log(msg: String) = println(msg)
    })

    val firebaseOptions = FirebaseOptions(
        applicationId = "1:853787459533:android:684014a704301123c84e84",
        apiKey = "",
        projectId = "cloudgallery-de527",
        storageBucket = "cloudgallery-de527.firebasestorage.app"
    )

    // Initialize Firebase for desktop
    Firebase.initialize(Application() ,options = firebaseOptions)

    Window(
        onCloseRequest = ::exitApplication,
        title = "CloudGallery"
    ) {
        App() // Your Compose UI
    }
}
