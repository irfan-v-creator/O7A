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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
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
fun SparePartsHubScreen(
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
        // Hero Title matching O7A Page 4
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "service",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            )
            Text(
                text = "Spare Parts",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan
                )
            )
            Text(
                text = "Genuine OEM & Certified EV scooter components",
                fontSize = 13.sp,
                color = O7ATextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Split Option 1: Online Purchase Options (Matching O7A Page 4)
        SparePartsEntryCard(
            title = "online purchase options",
            subtitle = "Direct home delivery • Full catalogue & search • UPI Payment",
            badge = "FAST DELIVERY",
            badgeColor = O7ASuccessGreen,
            icon = Icons.Default.LocalShipping,
            testTag = "spare_parts_online_button",
            onClick = { onNavigate(AppScreen.SPARE_PARTS_ONLINE) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Split Option 2: Offline Purchase Options (Matching O7A Page 4)
        SparePartsEntryCard(
            title = "Ofline purchase options",
            subtitle = "Authorized Service Centre counter stock • Same-day pickup & fitting",
            badge = "SERVICE COUNTER",
            badgeColor = O7AElectricBlue,
            icon = Icons.Default.Store,
            testTag = "spare_parts_offline_button",
            onClick = { onNavigate(AppScreen.SPARE_PARTS_OFFLINE) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Category Highlights preview
        Text(
            text = "POPULAR COMPONENT CATEGORIES",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 1.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MiniCategoryBadge("Brakes & Rotors", Icons.Default.Build, Modifier.weight(1f)) {
                onNavigate(AppScreen.SPARE_PARTS_ONLINE)
            }
            MiniCategoryBadge("Drive Belts", Icons.Default.ElectricMeter, Modifier.weight(1f)) {
                onNavigate(AppScreen.SPARE_PARTS_ONLINE)
            }
            MiniCategoryBadge("Fast Chargers", Icons.Default.ShoppingBag, Modifier.weight(1f)) {
                onNavigate(AppScreen.SPARE_PARTS_ONLINE)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SparePartsEntryCard(
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = O7ASurfaceNavy,
        tonalElevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, O7ACardBorder, RoundedCornerShape(20.dp))
            .testTag(testTag)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeColor.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(O7ADeepNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = O7ACyanGlow,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = O7ATextPrimary
            )

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = O7ATextSecondary,
                lineHeight = 17.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Explore options",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ACyanGlow
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = O7ACyanGlow,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun MiniCategoryBadge(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = O7ANavy,
        modifier = modifier
            .border(1.dp, O7ACardBorder, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = O7ANeonCyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = O7ATextPrimary,
                maxLines = 1
            )
        }
    }
}
