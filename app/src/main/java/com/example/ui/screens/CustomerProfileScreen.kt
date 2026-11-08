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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.UserProfile
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
fun CustomerProfileScreen(
    profile: UserProfile,
    onSaveProfile: (String, String, String, String, String) -> Unit,
    onToggleLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var fullName by remember(profile) { mutableStateOf(profile.fullName) }
    var phone by remember(profile) { mutableStateOf(profile.phone) }
    var email by remember(profile) { mutableStateOf(profile.email) }
    var address by remember(profile) { mutableStateOf(profile.address) }
    var preferredScooter by remember(profile) { mutableStateOf(profile.preferredScooter) }
    var savedSuccess by remember { mutableStateOf(false) }

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
                    text = "CUSTOMER REGISTRATION & SESSION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Registration & Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            }

            O7AStatusBadge(
                text = if (profile.isLoggedIn) "ACTIVE SESSION" else "LOGGED OUT",
                statusType = if (profile.isLoggedIn) "SUCCESS" else "WARNING"
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Profile Avatar and Quick Status
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(O7AElectricBlue.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = O7ACyanGlow,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = profile.fullName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                    Text(
                        text = profile.phone,
                        fontSize = 12.sp,
                        color = O7ATextSecondary
                    )
                    Text(
                        text = "Primary EV: ${profile.preferredScooter}",
                        fontSize = 11.sp,
                        color = O7ACyanGlow
                    )
                }

                Button(
                    onClick = onToggleLogin,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (profile.isLoggedIn) Color(0xFF7F1D1D) else O7AElectricBlue
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("session_toggle_button")
                ) {
                    Text(
                        text = if (profile.isLoggedIn) "Sign Out" else "Sign In",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Form Fields
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Personal & Contact Information",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
                Text(
                    text = "Used for parts order delivery and service appointments",
                    fontSize = 11.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        savedSuccess = false
                    },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = O7ANeonCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = O7ADeepNavy,
                        unfocusedContainerColor = O7ADeepNavy,
                        focusedBorderColor = O7ACyanGlow,
                        unfocusedBorderColor = O7ACardBorder,
                        focusedTextColor = O7ATextPrimary,
                        unfocusedTextColor = O7ATextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("profile_name_input")
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        savedSuccess = false
                    },
                    label = { Text("Phone Number") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = O7ANeonCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = O7ADeepNavy,
                        unfocusedContainerColor = O7ADeepNavy,
                        focusedBorderColor = O7ACyanGlow,
                        unfocusedBorderColor = O7ACardBorder,
                        focusedTextColor = O7ATextPrimary,
                        unfocusedTextColor = O7ATextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("profile_phone_input")
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        savedSuccess = false
                    },
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = O7ANeonCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = O7ADeepNavy,
                        unfocusedContainerColor = O7ADeepNavy,
                        focusedBorderColor = O7ACyanGlow,
                        unfocusedBorderColor = O7ACardBorder,
                        focusedTextColor = O7ATextPrimary,
                        unfocusedTextColor = O7ATextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("profile_email_input")
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        savedSuccess = false
                    },
                    label = { Text("Default Delivery Address") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = O7ANeonCyan) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = O7ADeepNavy,
                        unfocusedContainerColor = O7ADeepNavy,
                        focusedBorderColor = O7ACyanGlow,
                        unfocusedBorderColor = O7ACardBorder,
                        focusedTextColor = O7ATextPrimary,
                        unfocusedTextColor = O7ATextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("profile_address_input")
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = preferredScooter,
                    onValueChange = {
                        preferredScooter = it
                        savedSuccess = false
                    },
                    label = { Text("Primary Scooter Model") },
                    leadingIcon = { Icon(Icons.Default.ElectricScooter, contentDescription = null, tint = O7ANeonCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = O7ADeepNavy,
                        unfocusedContainerColor = O7ADeepNavy,
                        focusedBorderColor = O7ACyanGlow,
                        unfocusedBorderColor = O7ACardBorder,
                        focusedTextColor = O7ATextPrimary,
                        unfocusedTextColor = O7ATextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("profile_scooter_input")
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSaveProfile(fullName, phone, email, address, preferredScooter)
                        savedSuccess = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (savedSuccess) O7ASuccessGreen else O7AElectricBlue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("save_profile_button")
                ) {
                    Icon(
                        imageVector = if (savedSuccess) Icons.Default.Check else Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (savedSuccess) "Profile Updated Successfully" else "Save Registration Profile",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
