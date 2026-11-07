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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.AppointmentEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
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
fun AdminDashboardScreen(
    products: List<ProductEntity>,
    orders: List<OrderEntity>,
    appointments: List<AppointmentEntity>,
    onUpdateStock: (Long, Int) -> Unit,
    onVerifyPayment: (String, Boolean) -> Unit,
    onUpdateOrderStatus: (String, String) -> Unit,
    onUpdateAppointmentStatus: (Long, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Inventory", "Orders & UPI", "Appointments")

    val pendingPaymentsCount = orders.count { it.paymentStatus == "UNDER_REVIEW" }
    val lowStockCount = products.count { it.stockQty <= 10 }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(16.dp)
    ) {
        // Admin Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "OPERATIONS CONSOLE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7AWarningAmber,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Admin & Inventory Portal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            }

            O7AStatusBadge("ADMIN ACTIVE", "WARNING")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Metric Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AdminMetricPill(
                title = "Pending UTR",
                value = pendingPaymentsCount.toString(),
                color = if (pendingPaymentsCount > 0) O7AWarningAmber else O7ASuccessGreen,
                modifier = Modifier.weight(1f)
            )
            AdminMetricPill(
                title = "Low Stock",
                value = lowStockCount.toString(),
                color = if (lowStockCount > 0) O7AWarningAmber else O7ACyanGlow,
                modifier = Modifier.weight(1f)
            )
            AdminMetricPill(
                title = "Total Orders",
                value = orders.size.toString(),
                color = O7ANeonCyan,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Navigation
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = O7ASurfaceNavy,
            contentColor = O7ACyanGlow,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = O7ACyanGlow
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, O7ACardBorder, RoundedCornerShape(12.dp))
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) O7ACyanGlow else O7ATextSecondary
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Content Area per Tab
        when (selectedTab) {
            0 -> {
                // Tab 0: Inventory Management
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(products, key = { it.id }) { product ->
                        AdminProductStockCard(
                            product = product,
                            onUpdateStock = { newStock -> onUpdateStock(product.id, newStock) }
                        )
                    }
                }
            }
            1 -> {
                // Tab 1: Orders & Payment Verification
                if (orders.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No orders placed yet", color = O7ATextSecondary)
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(orders, key = { it.orderId }) { order ->
                            AdminOrderCard(
                                order = order,
                                onVerifyPayment = { approved -> onVerifyPayment(order.orderId, approved) },
                                onUpdateStatus = { newStatus -> onUpdateOrderStatus(order.orderId, newStatus) }
                            )
                        }
                    }
                }
            }
            2 -> {
                // Tab 2: Workshop Appointments
                if (appointments.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No appointments scheduled", color = O7ATextSecondary)
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(appointments, key = { it.id }) { appt ->
                            AdminAppointmentCard(
                                appointment = appt,
                                onUpdateStatus = { status -> onUpdateAppointmentStatus(appt.id, status) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminMetricPill(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = O7ASurfaceNavy,
        modifier = modifier.border(1.dp, O7ACardBorder, RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 10.sp, color = O7ATextSecondary, maxLines = 1)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Black, color = color)
        }
    }
}

@Composable
fun AdminProductStockCard(
    product: ProductEntity,
    onUpdateStock: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = O7ASurfaceNavy,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, O7ACardBorder, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary,
                    maxLines = 1
                )
                Text(
                    text = "SKU: ${product.sku} • ₹${product.price.toInt()}",
                    fontSize = 11.sp,
                    color = O7ATextSecondary
                )
            }

            // Inline Stock Adjuster
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = { onUpdateStock(maxOf(0, product.stockQty - 1)) },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(O7ADeepNavy)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = O7ATextPrimary, modifier = Modifier.size(14.dp))
                }

                Text(
                    text = "${product.stockQty}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (product.stockQty <= 5) O7AWarningAmber else O7ACyanGlow,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                IconButton(
                    onClick = { onUpdateStock(product.stockQty + 1) },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(O7ADeepNavy)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increase", tint = O7ACyanGlow, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun AdminOrderCard(
    order: OrderEntity,
    onVerifyPayment: (Boolean) -> Unit,
    onUpdateStatus: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
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
                Text(text = order.orderId, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = O7ACyanGlow)
                O7AStatusBadge(order.paymentStatus, if (order.paymentStatus == "VERIFIED") "SUCCESS" else "WARNING")
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${order.customerName} (${order.customerPhone})",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = O7ATextPrimary
            )
            Text(text = "Items: ${order.itemsSummary}", fontSize = 11.sp, color = O7ATextSecondary, maxLines = 2)

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "UTR: ${if (order.utrNumber.isNotBlank()) order.utrNumber else "None"}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan
                )
                Text(
                    text = "Total: ₹${order.totalAmount.toInt()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Payment verification action bar (Roadmap Section 5)
            if (order.paymentStatus != "VERIFIED") {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onVerifyPayment(true) },
                        colors = ButtonDefaults.buttonColors(containerColor = O7ASuccessGreen),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("admin_verify_payment_${order.orderId}")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Verify Payment", fontSize = 11.sp)
                    }

                    Button(
                        onClick = { onVerifyPayment(false) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF991B1B)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reject", fontSize = 11.sp)
                    }
                }
            } else {
                // Fulfillment Status Transitions: PROCESSING -> SHIPPED -> DELIVERED
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Status: ${order.orderStatus}", fontSize = 12.sp, color = O7ATextSecondary)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (order.orderStatus != "SHIPPED" && order.orderStatus != "DELIVERED") {
                            Button(
                                onClick = { onUpdateStatus("SHIPPED") },
                                colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Mark Shipped", fontSize = 11.sp)
                            }
                        }
                        if (order.orderStatus != "DELIVERED") {
                            Button(
                                onClick = { onUpdateStatus("DELIVERED") },
                                colors = ButtonDefaults.buttonColors(containerColor = O7ANavy),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Mark Delivered", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminAppointmentCard(
    appointment: AppointmentEntity,
    onUpdateStatus: (String) -> Unit
) {
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = appointment.bookingReference, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = O7ACyanGlow)
                O7AStatusBadge(appointment.status, "INFO")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${appointment.customerName} • ${appointment.customerPhone}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = O7ATextPrimary
            )
            Text(
                text = "${appointment.vehicleModel} - ${appointment.serviceType}",
                fontSize = 11.sp,
                color = O7ATextSecondary
            )
            Text(
                text = "Slot: ${appointment.appointmentDate} (${appointment.timeSlot})",
                fontSize = 11.sp,
                color = O7ANeonCyan
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (appointment.status != "COMPLETED") {
                    Button(
                        onClick = { onUpdateStatus("COMPLETED") },
                        colors = ButtonDefaults.buttonColors(containerColor = O7ASuccessGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Complete Service", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}
