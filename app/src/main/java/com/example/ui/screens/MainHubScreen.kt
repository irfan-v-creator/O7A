package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VehicleEntity
import com.example.ui.AppScreen
import com.example.ui.components.O7AGlassCard
import com.example.ui.components.O7AScooterArt
import com.example.ui.theme.O7ACardBorder
import com.example.ui.theme.O7ACyanGlow
import com.example.ui.theme.O7ADeepNavy
import com.example.ui.theme.O7AElectricBlue
import com.example.ui.theme.O7ANavy
import com.example.ui.theme.O7ANeonCyan
import com.example.ui.theme.O7ASuccessGreen
import com.example.ui.theme.O7ASurfaceNavy
import com.example.ui.theme.O7ATextPrimary
import com.example.ui.theme.O7ATextSecondary

@Composable
fun MainHubScreen(
    primaryVehicle: VehicleEntity?,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        O7ADeepNavy,
                        O7ANavy,
                        O7ADeepNavy
                    )
                )
            )
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top stylized O7A Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "O 7 A",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 12.sp,
                    color = Color.White
                )
            )
        }

        Text(
            text = "EV ECOSYSTEM & SPARE PARTS PLATFORM",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Hero Scooter Visual (matching O7A Page 1)
        O7AScooterArt(
            height = 240.dp,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clickable { onNavigate(AppScreen.CUSTOMER_HUB) }
        )

        // Connected Vehicle Pill (if registered)
        if (primaryVehicle != null) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = O7ASurfaceNavy.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(12.dp))
                    .clickable { onNavigate(AppScreen.VEHICLE_GARAGE) }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(O7AElectricBlue.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ElectricScooter,
                                contentDescription = null,
                                tint = O7ACyanGlow,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "${primaryVehicle.make} ${primaryVehicle.model}",
                                fontWeight = FontWeight.Bold,
                                color = O7ATextPrimary,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "${primaryVehicle.regNumber} • Battery ${primaryVehicle.batteryHealth}%",
                                color = O7ASuccessGreen,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "View Garage",
                        tint = O7ANeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Three Main Entry Portals (matching O7A Page 1 layout)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MainPortalCard(
                title = "customer",
                subtitle = "garage & support",
                icon = Icons.Default.Person,
                testTag = "main_portal_customer",
                onClick = { onNavigate(AppScreen.CUSTOMER_HUB) },
                modifier = Modifier.weight(1f)
            )

            MainPortalCard(
                title = "spare parts",
                subtitle = "online & offline",
                icon = Icons.Default.Settings,
                testTag = "main_portal_spare_parts",
                onClick = { onNavigate(AppScreen.SPARE_PARTS_HUB) },
                modifier = Modifier.weight(1f)
            )

            MainPortalCard(
                title = "service center",
                subtitle = "book & diagnostic",
                icon = Icons.Default.Build,
                testTag = "main_portal_service_center",
                onClick = { onNavigate(AppScreen.SERVICE_CENTER_HUB) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Scan CTA
        Surface(
            onClick = { onNavigate(AppScreen.SCANNER) },
            shape = RoundedCornerShape(16.dp),
            color = O7AElectricBlue.copy(alpha = 0.35f),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, O7ACyanGlow.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .testTag("main_quick_scan_button")
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(O7ACyanGlow.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "Scanner",
                            tint = O7ACyanGlow,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Instant QR & Barcode Scanner",
                            fontWeight = FontWeight.Bold,
                            color = O7ATextPrimary,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Scan EV part codes, chassis or vehicle VIN",
                            color = O7ATextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = O7ACyanGlow
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun MainPortalCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = O7ASurfaceNavy,
        tonalElevation = 6.dp,
        modifier = modifier
            .height(130.dp)
            .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(O7ADeepNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = O7ACyanGlow,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "↗",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan
                )
            }

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = O7ATextPrimary,
                    lineHeight = 16.sp
                )
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = O7ATextSecondary,
                    lineHeight = 12.sp
                )
            }
        }
    }
}
