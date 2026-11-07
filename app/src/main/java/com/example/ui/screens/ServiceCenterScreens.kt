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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppointmentEntity
import com.example.data.model.VehicleEntity
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
fun ServiceCenterHubScreen(
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero title matching O7A Page 3
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = "service",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            )
            Text(
                text = "Center",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan
                )
            )
            Text(
                text = "Professional EV maintenance, firmware calibration & workshop booking",
                fontSize = 13.sp,
                color = O7ATextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid cards matching O7A Page 3
        // Row 1: Video Assistants | Demo Video | Spare Parts
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomerActionTile(
                title = "video assistants",
                icon = Icons.Default.HeadsetMic,
                testTag = "sc_tile_video_assistants",
                onClick = { onNavigate(AppScreen.VIDEO_ASSISTANTS) },
                modifier = Modifier.weight(1f)
            )

            CustomerActionTile(
                title = "Demo video",
                icon = Icons.Default.PlayCircle,
                testTag = "sc_tile_demo_video",
                onClick = { onNavigate(AppScreen.DEMO_VIDEO) },
                modifier = Modifier.weight(1f)
            )

            CustomerActionTile(
                title = "Spare Parts",
                icon = Icons.Default.Settings,
                testTag = "sc_tile_spare_parts",
                onClick = { onNavigate(AppScreen.SPARE_PARTS_HUB) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Row 2: Software section service | Book + Appoinment (Matching O7A Page 3)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomerActionTile(
                title = "software section service",
                icon = Icons.Default.Computer,
                testTag = "sc_tile_software_service",
                onClick = { onNavigate(AppScreen.SOFTWARE_SERVICE) },
                modifier = Modifier.weight(1f)
            )

            CustomerActionTile(
                title = "Book + Appoinment",
                icon = Icons.Default.CalendarMonth,
                testTag = "sc_tile_book_appointment",
                onClick = { onNavigate(AppScreen.BOOK_APPOINTMENT) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Active Service Center info card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "NEXT AVAILABLE WORKSHOP SLOT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ACyanGlow
                )
                Text(
                    text = "O7A Central Hub, Metro EV Center",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = "Tomorrow at 10:30 AM • 4 Master Technicians on duty",
                    fontSize = 12.sp,
                    color = O7ASuccessGreen,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onNavigate(AppScreen.BOOK_APPOINTMENT) },
                    colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Instant Book Slot")
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun BookAppointmentScreen(
    vehicles: List<VehicleEntity>,
    onBookAppointment: (String, String, String, String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val serviceTypes = listOf(
        "General Periodic Service (15,000 km)",
        "Brake Pad & Hydraulic Fluid Overhaul",
        "ECU Software Diagnostics & BMS Check",
        "Final Drive Belt & Pulley Inspection",
        "Battery Health & Range Calibration"
    )
    val centers = listOf(
        "O7A Central Hub, Metro EV Center (Kochi)",
        "O7A TechHub Workshop, Kakkanad",
        "O7A Express Point, Edappally"
    )
    val timeSlots = listOf("10:00 AM", "11:30 AM", "02:00 PM", "04:30 PM")

    var selectedService by remember { mutableStateOf(serviceTypes[0]) }
    var selectedCenter by remember { mutableStateOf(centers[0]) }
    var selectedVehicleModel by remember {
        mutableStateOf(vehicles.firstOrNull()?.model ?: "Ola S1 Pro Gen 2")
    }
    var selectedSlot by remember { mutableStateOf(timeSlots[0]) }
    var customerName by remember { mutableStateOf("Rahul Nair") }
    var customerPhone by remember { mutableStateOf("+91 98470 12345") }
    var specialNotes by remember { mutableStateOf("Please check regen braking sensitivity.") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "SERVICE RESERVATION",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Book + Appointment",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Vehicle Selector
        Text(
            text = "SELECT REGISTERED VEHICLE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ACyanGlow,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            vehicles.forEach { v ->
                val isSelected = selectedVehicleModel == v.model
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedVehicleModel = v.model },
                    label = { Text("${v.make} ${v.model}", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = O7AElectricBlue,
                        selectedLabelColor = Color.White,
                        containerColor = O7ASurfaceNavy,
                        labelColor = O7ATextSecondary
                    )
                )
            }
        }

        // Service Type Options
        Text(
            text = "SERVICE TYPE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ACyanGlow,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(bottom = 14.dp)
        ) {
            serviceTypes.forEach { st ->
                val isSelected = selectedService == st
                Surface(
                    onClick = { selectedService = st },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) O7AElectricBlue.copy(alpha = 0.4f) else O7ASurfaceNavy,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            if (isSelected) O7ACyanGlow else O7ACardBorder,
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Build,
                            contentDescription = null,
                            tint = if (isSelected) O7ACyanGlow else O7ATextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = st,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = O7ATextPrimary
                        )
                    }
                }
            }
        }

        // Center selection
        Text(
            text = "SERVICE LOCATION",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ACyanGlow,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(bottom = 14.dp)
        ) {
            centers.forEach { c ->
                val isSelected = selectedCenter == c
                Surface(
                    onClick = { selectedCenter = c },
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) O7AElectricBlue.copy(alpha = 0.4f) else O7ASurfaceNavy,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isSelected) O7ACyanGlow else O7ACardBorder, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = c,
                        fontSize = 12.sp,
                        color = O7ATextPrimary,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Time slot selection
        Text(
            text = "PREFERRED TIME SLOT (TOMORROW)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ACyanGlow,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            timeSlots.forEach { slot ->
                val isSelected = selectedSlot == slot
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedSlot = slot },
                    label = { Text(slot, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = O7ACyanGlow.copy(alpha = 0.25f),
                        selectedLabelColor = O7ACyanGlow,
                        containerColor = O7ASurfaceNavy,
                        labelColor = O7ATextSecondary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Contact inputs
        OutlinedTextField(
            value = customerName,
            onValueChange = { customerName = it },
            label = { Text("Customer Name") },
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
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = customerPhone,
            onValueChange = { customerPhone = it },
            label = { Text("Contact Phone") },
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
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = specialNotes,
            onValueChange = { specialNotes = it },
            label = { Text("Special Instructions / Symptoms") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = O7ASurfaceNavy,
                unfocusedContainerColor = O7ASurfaceNavy,
                focusedBorderColor = O7ACyanGlow,
                unfocusedBorderColor = O7ACardBorder,
                focusedTextColor = O7ATextPrimary,
                unfocusedTextColor = O7ATextPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                onBookAppointment(
                    customerName,
                    customerPhone,
                    selectedVehicleModel,
                    selectedService,
                    selectedCenter,
                    "Tomorrow",
                    selectedSlot,
                    specialNotes
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_appointment_booking_button")
        ) {
            Text(
                text = "Confirm Workshop Booking",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun AppointmentSuccessScreen(
    appointment: AppointmentEntity?,
    onReturnHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = O7ASuccessGreen,
            modifier = Modifier.size(76.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Service Appointment Confirmed!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            textAlign = TextAlign.Center
        )

        if (appointment != null) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = O7ASurfaceNavy,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp)
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Booking Reference", fontSize = 11.sp, color = O7ATextSecondary)
                    Text(text = appointment.bookingReference, fontSize = 16.sp, fontWeight = FontWeight.Black, color = O7ACyanGlow)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Vehicle & Service", fontSize = 11.sp, color = O7ATextSecondary)
                    Text(text = "${appointment.vehicleModel} • ${appointment.serviceType}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Location & Slot", fontSize = 11.sp, color = O7ATextSecondary)
                    Text(text = "${appointment.centerName}\n${appointment.appointmentDate} at ${appointment.timeSlot}", fontSize = 13.sp, color = O7ANeonCyan)
                }
            }
        }

        Text(
            text = "Your slot is reserved. An SMS reminder will be sent 2 hours before your scheduled arrival.",
            fontSize = 12.sp,
            color = O7ATextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = onReturnHome,
            colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Back to Main Dashboard")
        }
    }
}
