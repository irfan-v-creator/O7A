package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ScannerResult
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

@Composable
fun ScannerScreen(
    scannerResult: ScannerResult?,
    onScanCode: (String) -> Unit,
    onAddToCart: (Long) -> Unit,
    onClearResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var manualCodeInput by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "scanner_laser")
    val laserYRatio by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_pos"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "HARDWARE & PART IDENTIFICATION",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "QR & Retail Barcode Scanner",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Viewfinder HUD Box
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .border(2.dp, O7ACyanGlow, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Laser Scan Line Animation
            Canvas(modifier = Modifier.fillMaxSize()) {
                val laserY = size.height * laserYRatio
                drawLine(
                    color = O7ACyanGlow,
                    start = Offset(20f, laserY),
                    end = Offset(size.width - 20f, laserY),
                    strokeWidth = 4f
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = O7ACyanGlow.copy(alpha = 0.4f),
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Align barcode within frame",
                    fontSize = 12.sp,
                    color = O7ATextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Manual Barcode Input
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = manualCodeInput,
                onValueChange = { manualCodeInput = it },
                placeholder = { Text("Enter SKU, barcode or VIN...", color = O7ATextMuted, fontSize = 12.sp) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = O7ASurfaceNavy,
                    unfocusedContainerColor = O7ASurfaceNavy,
                    focusedBorderColor = O7ACyanGlow,
                    unfocusedBorderColor = O7ACardBorder,
                    focusedTextColor = O7ATextPrimary,
                    unfocusedTextColor = O7ATextPrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("scanner_manual_code_input")
            )

            Button(
                onClick = {
                    if (manualCodeInput.isNotBlank()) {
                        onScanCode(manualCodeInput.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(52.dp)
                    .testTag("scanner_manual_submit_button")
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Preset Test Codes (Per Section 6 of Roadmap)
        Text(
            text = "SAMPLE PRODUCT BARCODES TO TEST",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PresetScanChip("Brake Pads", "8901234567890", Modifier.weight(1f)) { onScanCode("8901234567890") }
            PresetScanChip("Drive Belt", "8901234567892", Modifier.weight(1f)) { onScanCode("8901234567892") }
            PresetScanChip("TFT Display", "8901234567894", Modifier.weight(1f)) { onScanCode("8901234567894") }
            PresetScanChip("Scooter VIN", "MD9EV2410S198217", Modifier.weight(1f)) { onScanCode("MD9EV2410S198217") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scanner Result Card (if resolved)
        scannerResult?.let { res ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, if (res.matchedProduct != null) O7ASuccessGreen else O7ACardBorder, RoundedCornerShape(16.dp))
                    .testTag("scanner_result_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RESOLVED CODE: ${res.code}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = O7ANeonCyan
                        )
                        if (res.matchedProduct != null) {
                            O7AStatusBadge("GENUINE OEM", "SUCCESS")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = res.message,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )

                    res.matchedProduct?.let { prod ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "SKU: ${prod.sku} • Compatible with ${prod.compatibleModels}",
                            fontSize = 12.sp,
                            color = O7ATextSecondary
                        )
                        Text(
                            text = "Price: ₹${prod.price.toInt()} • In Stock: ${prod.stockQty} units",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = O7ACyanGlow,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { onAddToCart(prod.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = O7ASuccessGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Add Scanned Part to Cart")
                        }
                    }

                    res.matchedVehicleVin?.let { vin ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ola S1 Pro Gen 2 Chassis matched to verified customer garage profile.",
                            fontSize = 12.sp,
                            color = O7ATextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onClearResult,
                        colors = ButtonDefaults.buttonColors(containerColor = O7ANavy),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Clear & Scan Again")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun PresetScanChip(
    title: String,
    code: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = O7ANavy,
        modifier = modifier.border(1.dp, O7ACardBorder, RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = O7ACyanGlow, maxLines = 1)
        }
    }
}
