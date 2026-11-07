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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Store
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
import com.example.ui.components.O7AStatusBadge
import com.example.ui.theme.O7ACardBorder
import com.example.ui.theme.O7ACyanGlow
import com.example.ui.theme.O7ADeepNavy
import com.example.ui.theme.O7AElectricBlue
import com.example.ui.theme.O7ANeonCyan
import com.example.ui.theme.O7ASuccessGreen
import com.example.ui.theme.O7ASurfaceNavy
import com.example.ui.theme.O7ATextMuted
import com.example.ui.theme.O7ATextPrimary
import com.example.ui.theme.O7ATextSecondary
import kotlin.random.Random

@Composable
fun SparePartsOfflineScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var customerName by remember { mutableStateOf("Rahul Nair") }
    var customerPhone by remember { mutableStateOf("+91 98470 12345") }
    var partNeeded by remember { mutableStateOf("Ceramic Disc Brake Pads Set") }
    var reservationConfirmedCode by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "SERVICE CENTRE COUNTER",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Offline Purchase & Pickup",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        Text(
            text = "Check in-stock items across authorized service locations. Reserve parts for walk-in collection and fitting.",
            fontSize = 13.sp,
            color = O7ATextSecondary,
            lineHeight = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Workshop Locations
        Text(
            text = "AUTHORIZED COUNTER LOCATIONS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        WorkshopLocationCard(
            name = "O7A Central Hub, Metro EV Center",
            address = "Palarivattom Bypass, Kochi, Kerala",
            timing = "8:30 AM - 7:30 PM",
            phone = "+91 484 290 8800",
            stockStatus = "HIGH STOCK"
        )

        Spacer(modifier = Modifier.height(10.dp))

        WorkshopLocationCard(
            name = "O7A TechHub Workshop, Kakkanad",
            address = "Near Infopark Express Gate, Kakkanad",
            timing = "9:00 AM - 6:00 PM",
            phone = "+91 484 290 8811",
            stockStatus = "IN STOCK"
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Reservation Form
        if (reservationConfirmedCode == null) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Reserve Part for Counter Collection",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                    Text(
                        text = "Guarantees 24-hour stock hold at the service counter",
                        fontSize = 12.sp,
                        color = O7ATextSecondary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = customerName,
                        onValueChange = { customerName = it },
                        label = { Text("Customer Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = O7ADeepNavy,
                            unfocusedContainerColor = O7ADeepNavy,
                            focusedBorderColor = O7ACyanGlow,
                            unfocusedBorderColor = O7ACardBorder,
                            focusedTextColor = O7ATextPrimary,
                            unfocusedTextColor = O7ATextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customerPhone,
                        onValueChange = { customerPhone = it },
                        label = { Text("Contact Phone") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = O7ADeepNavy,
                            unfocusedContainerColor = O7ADeepNavy,
                            focusedBorderColor = O7ACyanGlow,
                            unfocusedBorderColor = O7ACardBorder,
                            focusedTextColor = O7ATextPrimary,
                            unfocusedTextColor = O7ATextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = partNeeded,
                        onValueChange = { partNeeded = it },
                        label = { Text("Part Name or SKU") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = O7ADeepNavy,
                            unfocusedContainerColor = O7ADeepNavy,
                            focusedBorderColor = O7ACyanGlow,
                            unfocusedBorderColor = O7ACardBorder,
                            focusedTextColor = O7ATextPrimary,
                            unfocusedTextColor = O7ATextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            reservationConfirmedCode = "O7A-RSV-${Random.nextInt(1000, 9999)}"
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reserve_part_button")
                    ) {
                        Text(
                            text = "Generate Pickup Reservation Slip",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        } else {
            // Reservation Confirmed Voucher
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, O7ASuccessGreen, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = O7ASuccessGreen,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Counter Reservation Confirmed",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                    Text(
                        text = "Reservation Code: $reservationConfirmedCode",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ACyanGlow,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "Present this code at O7A Central Hub counter to claim your $partNeeded.",
                        fontSize = 12.sp,
                        color = O7ATextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = { reservationConfirmedCode = null },
                        colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Create Another Reservation")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun WorkshopLocationCard(
    name: String,
    address: String,
    timing: String,
    phone: String,
    stockStatus: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = O7ASurfaceNavy,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, O7ACardBorder, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary,
                    modifier = Modifier.weight(1f)
                )
                O7AStatusBadge(stockStatus, "SUCCESS")
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = O7ANeonCyan, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = address, fontSize = 12.sp, color = O7ATextSecondary)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = O7ATextMuted, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = timing, fontSize = 12.sp, color = O7ATextMuted)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Call, contentDescription = null, tint = O7ACyanGlow, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = phone, fontSize = 12.sp, color = O7ACyanGlow)
            }
        }
    }
}
