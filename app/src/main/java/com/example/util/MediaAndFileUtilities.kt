package com.example.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Roadmap Section 7: Image Compression Utility
 * Resizes and compresses images to reduce network payload & storage costs
 * while maintaining part label / barcode readability.
 */
object ImageCompressor {

    data class CompressionResult(
        val originalSizeBytes: Long,
        val compressedSizeBytes: Long,
        val compressionRatioPercent: Int,
        val width: Int,
        val height: Int,
        val compressedFile: File?
    )

    /**
     * Resizes and compresses a Bitmap to a target maximum dimension and JPEG quality.
     */
    fun compressBitmap(
        bitmap: Bitmap,
        context: Context,
        maxDimension: Int = 1200,
        quality: Int = 80
    ): CompressionResult {
        val originalStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, originalStream)
        val originalSize = originalStream.size().toLong()

        // Calculate aspect-ratio preserved scaling
        val origWidth = bitmap.width
        val origHeight = bitmap.height
        val scaleFactor = if (origWidth > maxDimension || origHeight > maxDimension) {
            maxDimension.toFloat() / maxOf(origWidth, origHeight)
        } else {
            1.0f
        }

        val targetWidth = (origWidth * scaleFactor).toInt().coerceAtLeast(1)
        val targetHeight = (origHeight * scaleFactor).toInt().coerceAtLeast(1)

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)

        val outputDir = File(context.cacheDir, "compressed_parts")
        if (!outputDir.exists()) outputDir.mkdirs()

        val compressedFile = File(outputDir, "part_${System.currentTimeMillis()}.jpg")
        val fileOut = FileOutputStream(compressedFile)
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOut)
        fileOut.flush()
        fileOut.close()

        val compressedSize = compressedFile.length()
        val ratio = if (originalSize > 0) {
            (((originalSize - compressedSize).toFloat() / originalSize) * 100).toInt().coerceAtLeast(0)
        } else 0

        return CompressionResult(
            originalSizeBytes = originalSize,
            compressedSizeBytes = compressedSize,
            compressionRatioPercent = ratio,
            width = targetWidth,
            height = targetHeight,
            compressedFile = compressedFile
        )
    }

    /**
     * Creates a synthetic high-resolution spare part inspection image for testing
     * containing part label, SKU and barcode markings.
     */
    fun generateSamplePartBitmap(title: String, sku: String): Bitmap {
        val width = 1600
        val height = 1200
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val bgPaint = Paint().apply {
            color = android.graphics.Color.rgb(21, 36, 59)
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        val textPaint = Paint().apply {
            color = android.graphics.Color.rgb(0, 229, 255)
            textSize = 56f
            isAntiAlias = true
            isFakeBoldText = true
        }
        canvas.drawText("O7A GENUINE EV SPARE PART", 80f, 160f, textPaint)

        val subPaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 42f
            isAntiAlias = true
        }
        canvas.drawText("Name: $title", 80f, 260f, subPaint)
        canvas.drawText("SKU: $sku", 80f, 340f, subPaint)
        canvas.drawText("Date: Nov 2026 • Certified OEM Component", 80f, 420f, subPaint)

        // Draw synthetic barcode pattern
        val barcodePaint = Paint().apply {
            color = android.graphics.Color.WHITE
            strokeWidth = 8f
        }
        for (i in 0 until 50) {
            val x = 120f + i * 24f
            val lineThickness = if (i % 3 == 0) 14f else 6f
            barcodePaint.strokeWidth = lineThickness
            canvas.drawLine(x, 560f, x, 760f, barcodePaint)
        }

        return bitmap
    }
}

/**
 * Roadmap Section 7: ZIP Creation & Extraction Utility
 * Implements archive creation and safe extraction with strict Zip-Slip protection.
 */
object ZipUtility {

    data class ZipResult(
        val success: Boolean,
        val archiveFile: File? = null,
        val extractedFiles: List<File> = emptyList(),
        val totalBytes: Long = 0,
        val message: String
    )

    /**
     * Creates a ZIP archive from a list of files with compression.
     */
    fun createZip(
        filesToArchive: List<File>,
        destinationZipFile: File
    ): ZipResult {
        return try {
            destinationZipFile.parentFile?.mkdirs()
            val zipOut = ZipOutputStream(FileOutputStream(destinationZipFile))

            for (file in filesToArchive) {
                if (!file.exists()) continue
                val entry = ZipEntry(file.name)
                zipOut.putNextEntry(entry)

                val fileIn = FileInputStream(file)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (fileIn.read(buffer).also { bytesRead = it } != -1) {
                    zipOut.write(buffer, 0, bytesRead)
                }
                fileIn.close()
                zipOut.closeEntry()
            }
            zipOut.close()

            ZipResult(
                success = true,
                archiveFile = destinationZipFile,
                totalBytes = destinationZipFile.length(),
                message = "Successfully created ZIP archive (${destinationZipFile.length()} bytes)"
            )
        } catch (e: Exception) {
            ZipResult(success = false, message = "ZIP creation failed: ${e.message}")
        }
    }

    /**
     * Safely extracts a ZIP archive into a target directory.
     * Enforces Path Traversal (Zip Slip) protection.
     */
    fun extractZip(
        zipFile: File,
        destinationDir: File
    ): ZipResult {
        val extractedList = mutableListOf<File>()
        return try {
            if (!destinationDir.exists()) destinationDir.mkdirs()
            val destCanonicalPath = destinationDir.canonicalPath

            val zipIn = ZipInputStream(FileInputStream(zipFile))
            var entry = zipIn.nextEntry

            while (entry != null) {
                val newFile = File(destinationDir, entry.name)
                // Strict Zip Slip Security Check
                if (!newFile.canonicalPath.startsWith(destCanonicalPath + File.separator)) {
                    throw SecurityException("Zip Slip path traversal vulnerability detected in entry: ${entry.name}")
                }

                if (entry.isDirectory) {
                    newFile.mkdirs()
                } else {
                    newFile.parentFile?.mkdirs()
                    val fileOut = FileOutputStream(newFile)
                    val buffer = ByteArray(4096)
                    var count: Int
                    while (zipIn.read(buffer).also { count = it } != -1) {
                        fileOut.write(buffer, 0, count)
                    }
                    fileOut.close()
                    extractedList.add(newFile)
                }
                zipIn.closeEntry()
                entry = zipIn.nextEntry
            }
            zipIn.close()

            ZipResult(
                success = true,
                extractedFiles = extractedList,
                totalBytes = extractedList.sumOf { it.length() },
                message = "Safely extracted ${extractedList.size} files without path traversal."
            )
        } catch (e: Exception) {
            ZipResult(success = false, message = "ZIP extraction failed: ${e.message}")
        }
    }
}
