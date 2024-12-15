package pl.zajecia.cloud.cloudgallery

import com.google.firebase.auth.FirebaseAuth

private val auth: FirebaseAuth = FirebaseAuth.getInstance()

actual fun isUserLoggedIn(): Boolean {
    return auth.currentUser != null
}

actual fun signInWithEmailAndPassword(
    email: String,
    password: String,
    onResult: (success: Boolean, errorMessage: String?) -> Unit
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, null)
            } else {
                onResult(false, task.exception?.localizedMessage)
            }
        }
}