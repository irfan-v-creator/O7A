package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderEntity
import com.example.data.model.SupportTicketEntity
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
import com.example.ui.theme.O7AWarningAmber

@Composable
fun CallAndVisitScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "DIRECT CONTACT & LOCATION",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Call + Visit",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Emergency Roadside Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, O7ACyanGlow, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Call, contentDescription = null, tint = O7ACyanGlow, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "24/7 EV Helpline & Breakdown",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                }

                Text(
                    text = "Immediate assistance for battery depletion, motor errors, and flat tire towing.",
                    fontSize = 12.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Button(
                    onClick = {
                        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+914842908800"))
                        context.startActivity(dialIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("call_helpline_button")
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Call Helpline (+91 484 290 8800)")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Center Location
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "VISIT AUTHORIZED HEADQUARTERS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ACyanGlow
                )
                Text(
                    text = "O7A Central Hub, Metro EV Center",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = "Palarivattom Bypass Junction, NH 66, Kochi, Kerala - 682025",
                    fontSize = 13.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = O7ATextMuted, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Mon - Sat: 8:30 AM to 7:30 PM (Sun: 9:00 AM - 1:00 PM)", fontSize = 11.sp, color = O7ATextMuted)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:9.9981,76.3094?q=O7A+EV+Service+Center+Kochi"))
                        context.startActivity(mapIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = O7ANavy),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Get Navigation Directions")
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun TransferHigherDeptScreen(
    tickets: List<SupportTicketEntity>,
    onSubmitTicket: (String, String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val departments = listOf(
        "Higher Technical Engineering Team",
        "Battery Safety & Fire Advisory Board",
        "Warranty Escalations & Legal Desk",
        "Customer Executive Management"
    )

    var selectedDept by remember { mutableStateOf(departments[0]) }
    var customerName by remember { mutableStateOf("Rahul Nair") }
    var phone by remember { mutableStateOf("+91 98470 12345") }
    var vehicleModel by remember { mutableStateOf("Ola S1 Pro Gen 2") }
    var issueText by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("HIGH") }
    var ticketSuccessCode by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "ESCALATION DISPATCH",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7AWarningAmber,
            letterSpacing = 2.sp
        )
        Text(
            text = "Transfer to Higher Department",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        Text(
            text = "When normal service center resolution does not suffice, file an escalated request directly to senior technical management.",
            fontSize = 13.sp,
            color = O7ATextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (ticketSuccessCode != null) {
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
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = O7ASuccessGreen, modifier = Modifier.size(54.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Escalation Dispatched Successfully", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                    Text("Ticket Reference: $ticketSuccessCode", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = O7ACyanGlow, modifier = Modifier.padding(vertical = 4.dp))
                    Text("Assigned directly to $selectedDept with Priority $priority.", fontSize = 12.sp, color = O7ATextSecondary, textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { ticketSuccessCode = null },
                        colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue)
                    ) {
                        Text("Create Another Request")
                    }
                }
            }
        } else {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Escalation Target Department", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = O7ACyanGlow)

                    Spacer(modifier = Modifier.height(8.dp))

                    departments.forEach { dept ->
                        val isSelected = selectedDept == dept
                        Surface(
                            onClick = { selectedDept = dept },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) O7AElectricBlue.copy(alpha = 0.4f) else O7ADeepNavy,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(1.dp, if (isSelected) O7ACyanGlow else O7ACardBorder, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = dept,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = O7ATextPrimary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = issueText,
                        onValueChange = { issueText = it },
                        label = { Text("Detailed Problem Statement") },
                        minLines = 3,
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

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            if (issueText.isNotBlank()) {
                                onSubmitTicket(customerName, phone, selectedDept, vehicleModel, issueText, priority)
                                ticketSuccessCode = "O7A-ESC-${kotlin.random.Random.nextInt(100, 999)}"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = O7AWarningAmber),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_escalation_button")
                    ) {
                        Text("Dispatch Escalation Request", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun MonitoringOptionsScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "VEHICLE TELEMETRY & STATUS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Monitoring Options",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Battery State Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Battery State & Chemistry", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                    O7AStatusBadge("NMC 21700 CELLS", "SUCCESS")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Charge Level", fontSize = 11.sp, color = O7ATextSecondary)
                        Text("84%", fontSize = 24.sp, fontWeight = FontWeight.Black, color = O7ACyanGlow)
                    }
                    Column {
                        Text("Est. Range", fontSize = 11.sp, color = O7ATextSecondary)
                        Text("112 km", fontSize = 24.sp, fontWeight = FontWeight.Black, color = O7ASuccessGreen)
                    }
                    Column {
                        Text("Cell Health", fontSize = 11.sp, color = O7ATextSecondary)
                        Text("97% SoH", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Diagnostic Sensor Health Checklist
        Text(text = "SUBSYSTEM HEALTH CHECKLIST", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = O7ANeonCyan)

        Spacer(modifier = Modifier.height(8.dp))

        ChecklistRow("Regenerative Braking Calibration", "Calibrated / 0 DTC", true)
        ChecklistRow("Tire Pressure Sensors (TPMS)", "Front 33 PSI • Rear 36 PSI", true)
        ChecklistRow("Drive Belt Tension Ratio", "Optimal Deflection 12mm", true)
        ChecklistRow("Motor Inverter Temperature", "48°C (Normal operating temp)", true)
        ChecklistRow("Periodic Service Due", "Due in 3,160 km", false)

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun ChecklistRow(title: String, desc: String, isOk: Boolean) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = O7ASurfaceNavy,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(1.dp, O7ACardBorder, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isOk) Icons.Default.CheckCircle else Icons.Default.ReportProblem,
                contentDescription = null,
                tint = if (isOk) O7ASuccessGreen else O7AWarningAmber,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                Text(text = desc, fontSize = 11.sp, color = O7ATextSecondary)
            }
        }
    }
}

@Composable
fun VideoAssistantsScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    val tutorials = listOf(
        Pair("Replacing Front Ceramic Brake Pads", "Step-by-step caliper bleed and pad bed-in procedure (6:24 min)"),
        Pair("Belt Tension & Pulley Alignment", "How to adjust carbon drive belt tension using acoustic frequency (8:10 min)"),
        Pair("Smart TFT Screen Hard Reboot", "Resetting Bluetooth keyless unlock and touchscreen freezes (3:45 min)"),
        Pair("Home Charger Electrical Protection", "Setting up 16A earthing and voltage surge safety (5:12 min)")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "MULTIMEDIA KNOWLEDGE BASE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Video Assistants & Tutorials",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        tutorials.forEach { (title, subtitle) ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(14.dp))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(O7AElectricBlue.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = O7ACyanGlow,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                        Text(text = subtitle, fontSize = 11.sp, color = O7ATextSecondary, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun SoftwareServiceScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(16.dp)
    ) {
        Text(
            text = "DIAGNOSTICS & FIRMWARE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Software Section Service",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("ECU Firmware Version", fontSize = 13.sp, color = O7ATextSecondary)
                    O7AStatusBadge("v3.2.1-EV STABLE", "SUCCESS")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Telemetry Controller", fontSize = 13.sp, color = O7ATextSecondary)
                Text("OTA Channel: Official Production Release", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { /* trigger diagnostic scan */ },
                    colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.SystemUpdate, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Run Full Subsystem ECU Scan")
                }
            }
        }
    }
}

@Composable
fun DemoVideoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
                .border(1.5.dp, O7ACyanGlow, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.PlayCircle, contentDescription = "Play Demo", tint = O7ACyanGlow, modifier = Modifier.size(64.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "O7A EV Scooter & Platform Demo",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary
        )
        Text(
            text = "An in-depth tour of compatible spare parts, smart garage integration, and authorized service center booking.",
            fontSize = 13.sp,
            color = O7ATextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
fun OrderHistoryScreen(
    orders: List<OrderEntity>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(16.dp)
    ) {
        Text(
            text = "ORDER TRACKING",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "My Spare Parts Orders",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No orders placed yet", color = O7ATextSecondary)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(orders, key = { it.orderId }) { order ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = O7ASurfaceNavy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, O7ACardBorder, RoundedCornerShape(12.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = order.orderId, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = O7ACyanGlow)
                                O7AStatusBadge(order.paymentStatus, if (order.paymentStatus == "VERIFIED") "SUCCESS" else "WARNING")
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = order.itemsSummary, fontSize = 12.sp, color = O7ATextPrimary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Status: ${order.orderStatus}", fontSize = 11.sp, color = O7ATextSecondary)
                                Text(text = "₹${order.totalAmount.toInt()}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
