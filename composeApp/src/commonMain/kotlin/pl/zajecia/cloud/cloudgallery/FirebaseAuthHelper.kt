package pl.zajecia.cloud.cloudgallery

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

/**
 * Checks if the current user is logged in.
 */
fun isUserLoggedIn(): Boolean {
    // 'Firebase.auth' is provided by the GitLive Firebase library
    val auth = Firebase.auth
    return auth.currentUser != null
}

/**
 * Signs in a user using email and password.
 *
 * @param email The user's email.
 * @param password The user's password.
 * @param onResult A callback invoked when the sign-in completes.
 *                 success = true if sign-in succeeded, otherwise false
 *                 errorMessage = a string describing the error if any
 */
suspend fun signInWithEmailAndPassword(
    email: String,
    password: String,
    onResult: (success: Boolean, errorMessage: String?) -> Unit
) {
    val auth = Firebase.auth
    try {
        val result = auth.signInWithEmailAndPassword(email, password)
        result.user?.let {
            onResult(true, null)
        } ?: onResult(false, "Logging was unsuccessfull")
    } catch (e: Exception) {
        onResult(false, e.message)
    }
}
