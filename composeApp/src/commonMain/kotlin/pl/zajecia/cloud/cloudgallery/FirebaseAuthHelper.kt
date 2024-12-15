package pl.zajecia.cloud.cloudgallery

expect fun isUserLoggedIn(): Boolean

expect fun signInWithEmailAndPassword(
    email: String,
    password: String,
    onResult: (success: Boolean, errorMessage: String?) -> Unit
)