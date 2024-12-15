package pl.zajecia.cloud.cloudgallery

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

suspend fun fetchNasaImages(): List<NasaImage> {
    val firestore = Firebase.firestore
    return firestore.collection("nasa_images").snapshots
        .map { querySnapshot ->
            querySnapshot.documents.map { doc ->
                NasaImage(
                    title = doc.get("title"),
                    date = doc.get("date"),
                    publicUrl = doc.get("public_url"),
                    storagePath = doc.get("storage_path")
                )
            }
        }
        .first()
}
