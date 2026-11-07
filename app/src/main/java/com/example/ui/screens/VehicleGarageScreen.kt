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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.data.model.VehicleEntity
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
fun VehicleGarageScreen(
    vehicles: List<VehicleEntity>,
    onAddVehicle: (String, String, String, String, String, String) -> Unit,
    onSetPrimary: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CUSTOMER VEHICLES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Vehicle Garage & Profiles",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("add_vehicle_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add EV")
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (vehicles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No vehicles added yet. Tap 'Add EV' to register your scooter.", color = O7ATextSecondary)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(vehicles, key = { it.id }) { vehicle ->
                    VehicleGarageCard(
                        vehicle = vehicle,
                        onSetPrimary = { onSetPrimary(vehicle.id) }
                    )
                }
            }
        }
    }

    // Add Vehicle Modal
    if (showAddDialog) {
        var make by remember { mutableStateOf("Ola Electric") }
        var model by remember { mutableStateOf("S1 Air") }
        var variant by remember { mutableStateOf("3.0 kWh") }
        var regNumber by remember { mutableStateOf("KL-07-DJ-5512") }
        var vinNumber by remember { mutableStateOf("MD9EV2410AIR9910") }
        var batteryCapacity by remember { mutableStateOf("3.0 kWh") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            containerColor = O7ASurfaceNavy,
            title = {
                Text(
                    text = "Register EV Scooter",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = make,
                        onValueChange = { make = it },
                        label = { Text("Brand / Manufacturer") },
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

                    OutlinedTextField(
                        value = model,
                        onValueChange = { model = it },
                        label = { Text("Model Name") },
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

                    OutlinedTextField(
                        value = regNumber,
                        onValueChange = { regNumber = it },
                        label = { Text("Registration Number") },
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

                    OutlinedTextField(
                        value = vinNumber,
                        onValueChange = { vinNumber = it },
                        label = { Text("VIN / Chassis Number") },
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
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAddVehicle(make, model, variant, regNumber, vinNumber, batteryCapacity)
                        showAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue)
                ) {
                    Text("Save to Garage")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = O7ATextSecondary)
                }
            }
        )
    }
}

@Composable
fun VehicleGarageCard(
    vehicle: VehicleEntity,
    onSetPrimary: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.5.dp,
                if (vehicle.isPrimary) O7ACyanGlow else O7ACardBorder,
                RoundedCornerShape(16.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(O7ADeepNavy),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ElectricScooter,
                            contentDescription = null,
                            tint = O7ACyanGlow,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${vehicle.make} ${vehicle.model}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = O7ATextPrimary
                        )
                        Text(
                            text = "${vehicle.variant} • ${vehicle.regNumber}",
                            fontSize = 12.sp,
                            color = O7ATextSecondary
                        )
                    }
                }

                if (vehicle.isPrimary) {
                    O7AStatusBadge("PRIMARY SCOOTER", "SUCCESS")
                } else {
                    Button(
                        onClick = onSetPrimary,
                        colors = ButtonDefaults.buttonColors(containerColor = O7ANavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Set Active", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Battery Health & Odometer Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = O7ADeepNavy,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.BatteryChargingFull, contentDescription = null, tint = O7ASuccessGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Battery Health", fontSize = 11.sp, color = O7ATextSecondary)
                        }
                        Text("${vehicle.batteryHealth}%", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = O7ASuccessGreen)
                        LinearProgressIndicator(
                            progress = { vehicle.batteryHealth / 100f },
                            color = O7ASuccessGreen,
                            trackColor = Color(0xFF1E293B),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .height(4.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = O7ADeepNavy,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Speed, contentDescription = null, tint = O7ANeonCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Odometer", fontSize = 11.sp, color = O7ATextSecondary)
                        }
                        Text("${vehicle.currentOdoKm} km", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                        Text("Next service at ${vehicle.nextServiceKm} km", fontSize = 10.sp, color = O7ATextMuted)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "VIN: ${vehicle.vinNumber}", fontSize = 11.sp, color = O7ATextMuted)
                Text(text = "Warranty: ${vehicle.warrantyValidUntil}", fontSize = 11.sp, color = O7ANeonCyan)
            }
        }
    }
}
