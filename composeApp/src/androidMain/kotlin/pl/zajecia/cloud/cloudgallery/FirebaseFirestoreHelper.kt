package pl.zajecia.cloud.cloudgallery

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

actual suspend fun fetchNasaImages(): List<NasaImage> {
    val firestore = FirebaseFirestore.getInstance()
    val snapshot = firestore.collection("nasa_images").get().await()
    return snapshot.documents.mapNotNull { doc ->
        doc.toObject<NasaImage>()
    }
}