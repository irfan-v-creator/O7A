package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderEntity
import com.example.data.repository.CartItemWithProduct
import com.example.ui.AppScreen
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
fun CartScreen(
    cartItems: List<CartItemWithProduct>,
    onUpdateQty: (Long, Int) -> Unit,
    onRemoveItem: (Long) -> Unit,
    onProceedToCheckout: () -> Unit,
    onBrowseParts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subtotal = cartItems.sumOf { it.product.price * it.cartItem.quantity }
    val deliveryFee = if (subtotal > 1500 || subtotal == 0.0) 0.0 else 50.0
    val total = subtotal + deliveryFee

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
                    text = "YOUR ORDER",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "Shopping Cart (${cartItems.size} items)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = O7ATextMuted,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Your parts cart is empty",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                    Text(
                        text = "Explore the EV spare parts catalogue to add items",
                        fontSize = 13.sp,
                        color = O7ATextSecondary,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )
                    Button(
                        onClick = onBrowseParts,
                        colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Browse Spare Parts")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(cartItems, key = { it.cartItem.id }) { item ->
                    CartItemCard(
                        item = item,
                        onIncrease = { onUpdateQty(item.cartItem.id, item.cartItem.quantity + 1) },
                        onDecrease = { onUpdateQty(item.cartItem.id, item.cartItem.quantity - 1) },
                        onDelete = { onRemoveItem(item.cartItem.id) }
                    )
                }
            }

            // Order Price Breakdown Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Parts Subtotal", fontSize = 13.sp, color = O7ATextSecondary)
                        Text(text = "₹${subtotal.toInt()}", fontSize = 13.sp, color = O7ATextPrimary, fontWeight = FontWeight.Bold)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Delivery Fee", fontSize = 13.sp, color = O7ATextSecondary)
                        Text(
                            text = if (deliveryFee == 0.0) "FREE" else "₹${deliveryFee.toInt()}",
                            fontSize = 13.sp,
                            color = if (deliveryFee == 0.0) O7ASuccessGreen else O7ATextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Divider(color = O7ACardBorder, modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Total Payable", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                        Text(text = "₹${total.toInt()}", fontSize = 20.sp, fontWeight = FontWeight.Black, color = O7ACyanGlow)
                    }
                }
            }

            Button(
                onClick = onProceedToCheckout,
                colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("cart_proceed_checkout_button")
            ) {
                Text(
                    text = "Proceed to UPI Checkout (₹${total.toInt()})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItemWithProduct,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
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
                    text = item.product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary,
                    maxLines = 1
                )
                Text(
                    text = "SKU: ${item.product.sku} • ₹${item.product.price.toInt()} each",
                    fontSize = 12.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = "Line Total: ₹${(item.product.price * item.cartItem.quantity).toInt()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ACyanGlow,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Quantity adjusters
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(O7ADeepNavy)
                ) {
                    Icon(
                        imageVector = if (item.cartItem.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                        contentDescription = "Decrease",
                        tint = if (item.cartItem.quantity == 1) Color.Red else O7ATextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = item.cartItem.quantity.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )

                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(O7ADeepNavy)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = O7ACyanGlow,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CheckoutScreen(
    cartItems: List<CartItemWithProduct>,
    onPlaceOrder: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("Rahul Nair") }
    var phone by remember { mutableStateOf("+91 98470 12345") }
    var address by remember { mutableStateOf("Flat 4B, Skyline Ivy, Kakkanad, Kochi, Kerala - 682030") }
    var utrNumber by remember { mutableStateOf("") }
    var utrError by remember { mutableStateOf(false) }

    val subtotal = cartItems.sumOf { it.product.price * it.cartItem.quantity }
    val deliveryFee = if (subtotal > 1500) 0.0 else 50.0
    val total = subtotal + deliveryFee

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "CHECKOUT & PAYMENT",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = O7ANeonCyan,
            letterSpacing = 2.sp
        )
        Text(
            text = "Delivery & UPI Payment",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
        )

        // Delivery Form
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ASurfaceNavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, O7ACardBorder, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = "Delivery Details", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
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
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
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
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Shipping Address & Pincode") },
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Merchant UPI QR Code Card (Matching Roadmap Section 5 manual UPI MVP)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = O7ANavy),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, O7AElectricBlue, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "MERCHANT UPI PAYMENT",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ACyanGlow,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Scan QR with GPay / PhonePe / Paytm",
                    fontSize = 13.sp,
                    color = O7ATextSecondary,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                // High-fidelity Stylized QR Code Box
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    StylizedUPIQRCode()
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Merchant VPA: evspares.o7a@upi",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ATextPrimary
                )
                Text(
                    text = "Payable Amount: ₹${total.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = O7ACyanGlow,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                // UTR Entry Input
                OutlinedTextField(
                    value = utrNumber,
                    onValueChange = {
                        utrNumber = it
                        utrError = false
                    },
                    label = { Text("12-Digit UPI Reference / UTR Number") },
                    placeholder = { Text("e.g. 431289017264", color = O7ATextMuted) },
                    singleLine = true,
                    isError = utrError,
                    supportingText = {
                        Text(
                            text = if (utrError) "Please enter valid 12-digit UTR reference" else "Found in your UPI app transaction details",
                            color = if (utrError) Color.Red else O7ATextMuted,
                            fontSize = 11.sp
                        )
                    },
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
                        .testTag("checkout_utr_input")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (utrNumber.trim().length >= 8) {
                    onPlaceOrder(name, phone, address, utrNumber.trim())
                } else {
                    utrError = true
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = O7ASuccessGreen),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_order_button")
        ) {
            Text(
                text = "Submit Order with UTR Proof",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun StylizedUPIQRCode() {
    Canvas(modifier = Modifier.size(144.dp)) {
        val s = size.width
        val blockSize = s / 9f

        // Draw Corner Finder Patterns (Top-Left, Top-Right, Bottom-Left)
        drawRect(Color.Black, Offset(0f, 0f), Size(blockSize * 3, blockSize * 3))
        drawRect(Color.White, Offset(blockSize * 0.5f, blockSize * 0.5f), Size(blockSize * 2, blockSize * 2))
        drawRect(Color.Black, Offset(blockSize, blockSize), Size(blockSize, blockSize))

        drawRect(Color.Black, Offset(s - blockSize * 3, 0f), Size(blockSize * 3, blockSize * 3))
        drawRect(Color.White, Offset(s - blockSize * 2.5f, blockSize * 0.5f), Size(blockSize * 2, blockSize * 2))
        drawRect(Color.Black, Offset(s - blockSize * 2, blockSize), Size(blockSize, blockSize))

        drawRect(Color.Black, Offset(0f, s - blockSize * 3), Size(blockSize * 3, blockSize * 3))
        drawRect(Color.White, Offset(blockSize * 0.5f, s - blockSize * 2.5f), Size(blockSize * 2, blockSize * 2))
        drawRect(Color.Black, Offset(blockSize, s - blockSize * 2), Size(blockSize, blockSize))

        // Center Matrix Dots & Pattern
        val patternPoints = listOf(
            Pair(4, 0), Pair(4, 1), Pair(4, 3), Pair(4, 4), Pair(4, 5), Pair(4, 7), Pair(4, 8),
            Pair(0, 4), Pair(1, 4), Pair(3, 4), Pair(5, 4), Pair(7, 4), Pair(8, 4),
            Pair(3, 1), Pair(5, 1), Pair(1, 3), Pair(2, 5), Pair(6, 2), Pair(7, 6),
            Pair(5, 6), Pair(6, 5), Pair(3, 7), Pair(5, 8), Pair(7, 7)
        )

        patternPoints.forEach { (col, row) ->
            drawRect(
                color = Color.Black,
                topLeft = Offset(col * blockSize, row * blockSize),
                size = Size(blockSize, blockSize)
            )
        }
    }
}

@Composable
fun OrderSuccessScreen(
    order: OrderEntity?,
    onViewOrders: () -> Unit,
    onContinueShopping: () -> Unit,
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
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Order Submitted Successfully!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = O7ATextPrimary,
            textAlign = TextAlign.Center
        )

        if (order != null) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = O7ASurfaceNavy,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp)
                    .border(1.dp, O7ACardBorder, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Order ID", fontSize = 12.sp, color = O7ATextSecondary)
                        Text(text = order.orderId, fontSize = 14.sp, fontWeight = FontWeight.Black, color = O7ACyanGlow)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Payment Status", fontSize = 12.sp, color = O7ATextSecondary)
                        O7AStatusBadge(order.paymentStatus, "WARNING")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "UTR Submitted", fontSize = 12.sp, color = O7ATextSecondary)
                        Text(text = order.utrNumber, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = O7ATextPrimary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Total Amount", fontSize = 12.sp, color = O7ATextSecondary)
                        Text(text = "₹${order.totalAmount.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
                    }
                }
            }
        }

        Text(
            text = "Our admin team will verify your UTR reference and transition the order to Confirmed & Processing.",
            fontSize = 12.sp,
            color = O7ATextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = onViewOrders,
            colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("success_view_orders_button")
        ) {
            Text("Track Order in Orders List")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onContinueShopping,
            colors = ButtonDefaults.buttonColors(containerColor = O7ANavy),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Back to Spare Parts")
        }
    }
}
