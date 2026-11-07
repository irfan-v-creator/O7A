package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.UserRole
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
import com.example.ui.theme.O7AWarningAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun O7ATopBar(
    currentScreen: AppScreen,
    currentRole: UserRole,
    cartCount: Int,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onCartClick: () -> Unit,
    onRoleSelect: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    var roleMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onHomeClick() }
            ) {
                // O7A Stylized Brand
                Text(
                    text = "O7A",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp,
                        color = O7ACyanGlow
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(O7AElectricBlue.copy(alpha = 0.4f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "EV PLATFORM",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ANeonCyan
                    )
                }
            }
        },
        navigationIcon = {
            if (currentScreen != AppScreen.MAIN_HUB) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = O7ATextPrimary
                    )
                }
            } else {
                IconButton(
                    onClick = onHomeClick,
                    modifier = Modifier.testTag("top_bar_home_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.TwoWheeler,
                        contentDescription = "O7A Home",
                        tint = O7ACyanGlow
                    )
                }
            }
        },
        actions = {
            // Cart Icon with Badge
            IconButton(
                onClick = onCartClick,
                modifier = Modifier.testTag("top_bar_cart_button")
            ) {
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(
                                containerColor = O7ACyanGlow,
                                contentColor = O7ADeepNavy
                            ) {
                                Text(
                                    text = cartCount.toString(),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = if (cartCount > 0) O7ACyanGlow else O7ATextPrimary
                    )
                }
            }

            // Role Switcher Button
            Box {
                IconButton(
                    onClick = { roleMenuExpanded = true },
                    modifier = Modifier.testTag("role_switcher_button")
                ) {
                    Icon(
                        imageVector = when (currentRole) {
                            UserRole.CUSTOMER -> Icons.Default.Person
                            UserRole.SERVICE_STAFF -> Icons.Default.TwoWheeler
                            UserRole.ADMIN -> Icons.Default.AdminPanelSettings
                        },
                        contentDescription = "Select Role",
                        tint = if (currentRole == UserRole.ADMIN) O7AWarningAmber else O7ANeonCyan
                    )
                }

                DropdownMenu(
                    expanded = roleMenuExpanded,
                    onDismissRequest = { roleMenuExpanded = false },
                    modifier = Modifier
                        .background(O7ANavy)
                        .border(1.dp, O7ACardBorder, RoundedCornerShape(8.dp))
                ) {
                    UserRole.values().forEach { role ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = role.label,
                                    color = if (role == currentRole) O7ACyanGlow else O7ATextPrimary,
                                    fontWeight = if (role == currentRole) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onRoleSelect(role)
                                roleMenuExpanded = false
                            }
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = O7ADeepNavy,
            titleContentColor = O7ATextPrimary
        ),
        modifier = modifier
    )
}

@Composable
fun O7AGlassCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = O7ACardBorder,
    backgroundColor: Color = O7ASurfaceNavy,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        tonalElevation = 4.dp,
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        content()
    }
}

@Composable
fun O7AStatusBadge(
    text: String,
    statusType: String, // SUCCESS, WARNING, INFO
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (statusType.uppercase()) {
        "SUCCESS", "VERIFIED", "CONFIRMED", "DELIVERED" -> Pair(
            O7ASuccessGreen.copy(alpha = 0.2f),
            O7ASuccessGreen
        )
        "WARNING", "UNDER_REVIEW", "PENDING", "LOW_STOCK" -> Pair(
            O7AWarningAmber.copy(alpha = 0.2f),
            O7AWarningAmber
        )
        else -> Pair(
            O7AElectricBlue.copy(alpha = 0.3f),
            O7ACyanGlow
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

/**
 * High-fidelity native Canvas illustration of the iconic O7A EV front view
 * featuring the illuminated dual-halo headlight, handlebars, and metallic cowl.
 */
@Composable
fun O7AScooterArt(
    modifier: Modifier = Modifier,
    height: Dp = 220.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(height)) {
            val width = size.width
            val canvasHeight = size.height
            val centerX = width / 2f

            // Futuristic ambient gradient glow behind scooter
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        O7ACyanGlow.copy(alpha = 0.25f),
                        O7AElectricBlue.copy(alpha = 0.15f),
                        Color.Transparent
                    ),
                    center = Offset(centerX, canvasHeight * 0.5f),
                    radius = width * 0.45f
                )
            )

            // Handlebar / Mirrors
            val handlebarY = canvasHeight * 0.32f
            drawLine(
                color = Color(0xFF475569),
                start = Offset(centerX - 130f, handlebarY - 30f),
                end = Offset(centerX + 130f, handlebarY - 30f),
                strokeWidth = 10f,
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            // Left Mirror
            drawOval(
                color = Color(0xFF1E293B),
                topLeft = Offset(centerX - 170f, handlebarY - 60f),
                size = androidx.compose.ui.geometry.Size(40f, 25f)
            )
            // Right Mirror
            drawOval(
                color = Color(0xFF1E293B),
                topLeft = Offset(centerX + 130f, handlebarY - 60f),
                size = androidx.compose.ui.geometry.Size(40f, 25f)
            )

            // Front Cowl body shape
            val cowlPath = Path().apply {
                moveTo(centerX - 90f, handlebarY)
                lineTo(centerX + 90f, handlebarY)
                cubicTo(
                    centerX + 110f, handlebarY + 50f,
                    centerX + 85f, canvasHeight * 0.95f,
                    centerX + 50f, canvasHeight
                )
                lineTo(centerX - 50f, canvasHeight)
                cubicTo(
                    centerX - 85f, canvasHeight * 0.95f,
                    centerX - 110f, handlebarY + 50f,
                    centerX - 90f, handlebarY
                )
                close()
            }

            drawPath(
                path = cowlPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E293B),
                        Color(0xFF0F172A),
                        Color(0xFF020617)
                    )
                )
            )

            drawPath(
                path = cowlPath,
                color = O7ACardBorder,
                style = Stroke(width = 2.5f)
            )

            // Iconic O7A Halo Headlight Capsule
            val lightCenterY = handlebarY + 45f
            val lightWidth = 120f
            val lightHeight = 45f

            // Outer glow ring
            drawRoundRect(
                color = O7ACyanGlow,
                topLeft = Offset(centerX - lightWidth / 2f, lightCenterY - lightHeight / 2f),
                size = androidx.compose.ui.geometry.Size(lightWidth, lightHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(22f, 22f),
                style = Stroke(width = 6f)
            )

            // Inner LED core
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(centerX - (lightWidth - 14f) / 2f, lightCenterY - (lightHeight - 14f) / 2f),
                size = androidx.compose.ui.geometry.Size(lightWidth - 14f, lightHeight - 14f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f),
                style = Stroke(width = 3.5f)
            )

            // Dual projector lenses
            drawCircle(
                color = O7ACyanGlow.copy(alpha = 0.8f),
                center = Offset(centerX - 24f, lightCenterY),
                radius = 10f
            )
            drawCircle(
                color = Color.White,
                center = Offset(centerX - 24f, lightCenterY),
                radius = 5f
            )

            drawCircle(
                color = O7ACyanGlow.copy(alpha = 0.8f),
                center = Offset(centerX + 24f, lightCenterY),
                radius = 10f
            )
            drawCircle(
                color = Color.White,
                center = Offset(centerX + 24f, lightCenterY),
                radius = 5f
            )
        }
    }
}
