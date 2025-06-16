// BarangDialog.kt - Inside your BarangDialog() Composable

package com.anjelitahp0044.assessment3_mobpro.screen

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Import clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.anjelitahp0044.assessment3_mobpro.R
import com.anjelitahp0044.assessment3_mobpro.model.Barang
import com.anjelitahp0044.assessment3_mobpro.network.BarangApi
import com.anjelitahp0044.assessment3_mobpro.ui.theme.Assessment3_MobproTheme

@Composable
fun BarangDialog(
    bitmap: Bitmap?, // New image selected by user
    barang: Barang? = null, // Existing barang data for editing
    onDismissRequest: () -> Unit,
    onImagePickRequest: () -> Unit, // **** NEW: Lambda to request image picking ****
    onConfirmation: (String?, String, String) -> Unit
) {
    var nama by remember { mutableStateOf(barang?.nama ?: "") }
    var deskripsi by remember { mutableStateOf(barang?.deskripsi ?: "") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Display Image - Now clickable!
                val imageUrl = if (barang != null) BarangApi.getBarangUrl(barang.imageId) else null

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { onImagePickRequest() } // **** Make the image area clickable ****
                        .background(androidx.compose.ui.graphics.Color.LightGray), // Placeholder background
                    contentAlignment = Alignment.Center
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize() // Fill the Box
                        )
                    } else if (imageUrl != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(), // Fill the Box
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop // Ensure proper scaling
                        )
                    } else {
                        Text(text = "Tap to Add Image") // Guidance for new items
                    }
                }


                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text(text = stringResource(id = R.string.nama)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = deskripsi,
                    onValueChange = { deskripsi = it },
                    label = { Text(text = stringResource(id = R.string.deskripsi_des)) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.batal))
                    }
                    OutlinedButton(
                        onClick = { onConfirmation(barang?.id, nama, deskripsi) },
                        enabled = nama.isNotEmpty() && deskripsi.isNotEmpty(),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = if (barang == null) stringResource(R.string.simpan) else stringResource(R.string.update))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BarangDialogPreview() {
    Assessment3_MobproTheme {
        BarangDialog(
            bitmap = null,
            onDismissRequest = {},
            onImagePickRequest = {}, // Provide empty lambda for preview
            onConfirmation = { _, _, _ -> }
        )
    }
}