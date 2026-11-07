package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.VideoLibrary
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
import com.example.ui.AppScreen
import com.example.ui.theme.O7ACardBorder
import com.example.ui.theme.O7ACyanGlow
import com.example.ui.theme.O7ADeepNavy
import com.example.ui.theme.O7AElectricBlue
import com.example.ui.theme.O7ANavy
import com.example.ui.theme.O7ANeonCyan
import com.example.ui.theme.O7ASurfaceNavy
import com.example.ui.theme.O7ATextPrimary
import com.example.ui.theme.O7ATextSecondary

@Composable
fun CustomerHubScreen(
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
        Text(
            text = "O 7 A",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 8.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "CUSTOMER HUB",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = O7ACyanGlow,
            letterSpacing = 3.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Large Prominent Central Scanner Button (Matching O7A Page 2)
        Surface(
            onClick = { onNavigate(AppScreen.SCANNER) },
            shape = RoundedCornerShape(20.dp),
            color = O7ASurfaceNavy,
            modifier = Modifier
                .size(110.dp)
                .border(2.5.dp, O7ACyanGlow, RoundedCornerShape(20.dp))
                .testTag("customer_hub_scanner_button")
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(O7ACyanGlow.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = "Scanner",
                        tint = O7ACyanGlow,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "scanner",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Grid of 8 Functional Cards (Matching O7A Page 2 exactly)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Row 1: Video Assistants | Registration
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomerActionTile(
                    title = "video assistants",
                    icon = Icons.Default.HeadsetMic,
                    testTag = "customer_tile_video_assistants",
                    onClick = { onNavigate(AppScreen.VIDEO_ASSISTANTS) },
                    modifier = Modifier.weight(1f)
                )

                CustomerActionTile(
                    title = "registration",
                    icon = Icons.Default.Assignment,
                    testTag = "customer_tile_registration",
                    onClick = { onNavigate(AppScreen.VEHICLE_GARAGE) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Row 2: Demo Video | Call + Visit
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomerActionTile(
                    title = "Demo video",
                    icon = Icons.Default.PlayCircle,
                    testTag = "customer_tile_demo_video",
                    onClick = { onNavigate(AppScreen.DEMO_VIDEO) },
                    modifier = Modifier.weight(1f)
                )

                CustomerActionTile(
                    title = "Call + Visit",
                    icon = Icons.Default.Call,
                    testTag = "customer_tile_call_visit",
                    onClick = { onNavigate(AppScreen.CALL_AND_VISIT) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Row 3: Video Assistants (Guides) | Monitoring Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomerActionTile(
                    title = "video assistants (guides)",
                    icon = Icons.Default.VideoLibrary,
                    testTag = "customer_tile_video_guides",
                    onClick = { onNavigate(AppScreen.VIDEO_ASSISTANTS) },
                    modifier = Modifier.weight(1f)
                )

                CustomerActionTile(
                    title = "monitoring options",
                    icon = Icons.Default.MonitorHeart,
                    testTag = "customer_tile_monitoring",
                    onClick = { onNavigate(AppScreen.MONITORING_OPTIONS) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Row 4: Transfer to Higher Dept | Vehicle Related Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomerActionTile(
                    title = "Transfer to higher department",
                    icon = Icons.Default.SupportAgent,
                    testTag = "customer_tile_higher_dept",
                    onClick = { onNavigate(AppScreen.TRANSFER_HIGHER_DEPT) },
                    modifier = Modifier.weight(1f)
                )

                CustomerActionTile(
                    title = "Vehicle related information",
                    icon = Icons.Default.ElectricScooter,
                    testTag = "customer_tile_vehicle_info",
                    onClick = { onNavigate(AppScreen.VEHICLE_GARAGE) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CustomerActionTile(
    title: String,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = O7ASurfaceNavy,
        tonalElevation = 4.dp,
        modifier = modifier
            .height(84.dp)
            .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(O7ADeepNavy),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = O7ANeonCyan,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = O7ATextPrimary,
                lineHeight = 15.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
