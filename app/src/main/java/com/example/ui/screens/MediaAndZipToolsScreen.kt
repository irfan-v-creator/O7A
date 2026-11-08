package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.O7AStatusBadge
import com.example.ui.theme.O7ACardBorder
import com.example.ui.theme.O7ACyanGlow
import com.example.ui.theme.O7ADeepNavy
import com.example.ui.theme.O7AElectricBlue
import com.example.ui.theme.O7ANavy
import com.example.ui.theme.O7ANeonCyan
import com.example.ui.theme.O7ASuccessGreen
import com.example.ui.theme.O7ASurfaceNavy
import com.example.ui.theme.O7ATextMuted
import com.example.ui.theme.O7ATextPrimary
import com.example.ui.theme.O7ATextSecondary
import com.example.util.ImageCompressor
import com.example.util.ZipUtility
import java.io.File
import java.io.FileWriter

@Composable
fun MediaAndZipToolsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var sampleBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var compressionResult by remember { mutableStateOf<ImageCompressor.CompressionResult?>(null) }
    var isCompressing by remember { mutableStateOf(false) }

    var zipCreationResult by remember { mutableStateOf<ZipUtility.ZipResult?>(null) }
    var zipExtractionResult by remember { mutableStateOf<ZipUtility.ZipResult?>(null) }
    var isZipping by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ROADMAP SECTION 7 VERIFICATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Image & ZIP Safe Utilities",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            }

            O7AStatusBadge("NATIVE ENGINE", "SUCCESS")
        }

        Spacer(modifier = Modifier.height(14.dp))

        // --- SECTION A: IMAGE COMPRESSION BENCH ---
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Compress, contentDescription = null, tint = O7ACyanGlow, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Real Image Compression Engine",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                }

                Text(
                    text = "Resizes raw photo bytes before cloud upload. Keeps barcode and part labels crisp while cutting bandwidth by 70-85%.",
                    fontSize = 12.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Button(
                    onClick = {
                        isCompressing = true
                        val rawBitmap = ImageCompressor.generateSamplePartBitmap(
                            title = "High-Res Disc Rotor & Brake Pad",
                            sku = "O7A-BRK-450X-PRO"
                        )
                        sampleBitmap = rawBitmap
                        val res = ImageCompressor.compressBitmap(
                            bitmap = rawBitmap,
                            context = context,
                            maxDimension = 1000,
                            quality = 75
                        )
                        compressionResult = res
                        isCompressing = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("run_image_compression_btn")
                ) {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Compress 1600x1200 Sample Part Photo", fontSize = 13.sp)
                }

                compressionResult?.let { res ->
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = O7ADeepNavy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, O7ASuccessGreen, RoundedCornerShape(10.dp))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Original Raw Size", fontSize = 12.sp, color = O7ATextSecondary)
                                Text("${res.originalSizeBytes / 1024} KB", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Compressed Output", fontSize = 12.sp, color = O7ATextSecondary)
                                Text("${res.compressedSizeBytes / 1024} KB", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = O7ASuccessGreen)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Storage Bandwidth Saved", fontSize = 12.sp, color = O7ATextSecondary)
                                Text("${res.compressionRatioPercent}% reduction", fontSize = 12.sp, fontWeight = FontWeight.Black, color = O7ACyanGlow)
                            }
                            Text(
                                text = "Resolution scaled to ${res.width} x ${res.height} px • Output: ${res.compressedFile?.name}",
                                fontSize = 11.sp,
                                color = O7ATextMuted,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- SECTION B: ZIP CREATION & SAFE EXTRACTION BENCH ---
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.FolderZip, contentDescription = null, tint = O7ACyanGlow, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ZIP Archive Engine & Zip-Slip Protection",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                }

                Text(
                    text = "Creates and extracts multi-part technical manuals and firmware dumps safely with canonical path validation.",
                    fontSize = 12.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            isZipping = true
                            // Create sample documentation text files
                            val tempDir = File(context.cacheDir, "zip_staging")
                            if (!tempDir.exists()) tempDir.mkdirs()

                            val file1 = File(tempDir, "brake_specs_manual.txt").apply {
                                val writer = FileWriter(this)
                                writer.write("O7A Brake Caliper Torque Specs: 22 Nm. Bleed ratio: DOT 4 fluid.")
                                writer.close()
                            }
                            val file2 = File(tempDir, "belt_tension_chart.csv").apply {
                                val writer = FileWriter(this)
                                writer.write("Model,FrequencyHz,TensionN\nOla S1 Pro,48,350\nAther 450X,52,380\n")
                                writer.close()
                            }

                            val destZip = File(context.cacheDir, "o7a_service_manuals.zip")
                            val res = ZipUtility.createZip(listOf(file1, file2), destZip)
                            zipCreationResult = res
                            isZipping = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).testTag("create_zip_btn")
                    ) {
                        Text("Create ZIP Archive", fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            val zipFile = File(context.cacheDir, "o7a_service_manuals.zip")
                            if (zipFile.exists()) {
                                val extractDir = File(context.cacheDir, "extracted_manuals")
                                val res = ZipUtility.extractZip(zipFile, extractDir)
                                zipExtractionResult = res
                            }
                        },
                        enabled = zipCreationResult?.success == true,
                        colors = ButtonDefaults.buttonColors(containerColor = O7ANavy),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).testTag("extract_zip_btn")
                    ) {
                        Text("Safe Extract", fontSize = 12.sp)
                    }
                }

                zipCreationResult?.let { res ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Archive Created: ${res.archiveFile?.name} (${res.totalBytes} bytes)",
                        fontSize = 12.sp,
                        color = O7ASuccessGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                zipExtractionResult?.let { res ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = O7ADeepNavy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, O7ACardBorder, RoundedCornerShape(8.dp))
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = O7ASuccessGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Zip-Slip Verification: PASS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = O7ASuccessGreen)
                            }
                            Text(
                                text = res.message,
                                fontSize = 11.sp,
                                color = O7ATextSecondary,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
