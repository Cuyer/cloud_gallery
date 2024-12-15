package pl.zajecia.cloud.cloudgallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var nasaImages by remember { mutableStateOf<List<NasaImage>>(emptyList()) }
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    // Firestore fetch
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val images = fetchNasaImages()
                nasaImages = images
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Full-size image dialog
    if (selectedImageUrl != null) {
        FullSizeImageDialog(
            imageUrl = selectedImageUrl!!,
            onDismiss = { selectedImageUrl = null }
        )
    }

    if (getPlatform().name.contains("Android")) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(nasaImages.size) { index ->
                val item = nasaImages[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = item.date, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5), // 5 images across
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(nasaImages.size) { index ->
                val item = nasaImages[index]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clickable { selectedImageUrl = item.publicUrl },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    AsyncImage(url = item.publicUrl)
                }
            }
        }
    }
}

/**
 * Simple composable that loads image from a URL using a background thread and displays it.
 * For production, you’d use a library like Coil or Kamel for Compose Multiplatform.
 */
@Composable
fun AsyncImage(url: String, modifier: Modifier = Modifier) {


}



@Composable
fun FullSizeImageDialog(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        text = {
            Box(modifier = Modifier.sizeIn(minWidth = 300.dp, minHeight = 300.dp)) {
                AsyncImage(url = imageUrl)
            }
        }
    )
}