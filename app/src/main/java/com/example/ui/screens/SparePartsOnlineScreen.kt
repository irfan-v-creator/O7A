package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.ProductEntity
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
import com.example.ui.theme.O7AWarningAmber

@Composable
fun SparePartsOnlineScreen(
    products: List<ProductEntity>,
    selectedCategory: String,
    selectedVehicleFilter: String,
    searchQuery: String,
    onCategoryChange: (String) -> Unit,
    onVehicleFilterChange: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    onAddToCart: (Long) -> Unit,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Brakes", "Powertrain", "Electrical", "Battery", "Body", "Accessories")
    val vehicleModels = listOf("All", "Ola S1 Pro", "Ather 450X", "TVS iQube", "Bajaj Chetak")

    var detailsDialogProduct by remember { mutableStateOf<ProductEntity?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(O7ADeepNavy)
            .padding(horizontal = 16.dp)
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search by part name, SKU, or model...", color = O7ATextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = O7ANeonCyan
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = O7ATextSecondary)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
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
                .padding(top = 10.dp, bottom = 8.dp)
                .testTag("online_parts_search_input")
        )

        // Vehicle Compatibility Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Vehicle:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = O7ATextMuted,
                modifier = Modifier.padding(end = 4.dp)
            )

            vehicleModels.forEach { model ->
                val isSelected = selectedVehicleFilter == model
                FilterChip(
                    selected = isSelected,
                    onClick = { onVehicleFilterChange(model) },
                    label = { Text(model, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = O7AElectricBlue,
                        selectedLabelColor = Color.White,
                        containerColor = O7ANavy,
                        labelColor = O7ATextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) O7ACyanGlow else O7ACardBorder
                    ),
                    modifier = Modifier.height(32.dp)
                )
            }
        }

        // Category Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategoryChange(category) },
                    label = { Text(category, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = O7ACyanGlow.copy(alpha = 0.2f),
                        selectedLabelColor = O7ACyanGlow,
                        containerColor = O7ANavy,
                        labelColor = O7ATextPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) O7ACyanGlow else O7ACardBorder
                    ),
                    modifier = Modifier.height(34.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Product Listings
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        tint = O7ATextMuted,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No compatible spare parts found",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                    Text(
                        text = "Try clearing filters or checking another scooter model",
                        fontSize = 13.sp,
                        color = O7ATextSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        onAddToCart = { onAddToCart(product.id) },
                        onDetailsClick = { detailsDialogProduct = product }
                    )
                }
            }
        }
    }

    // Product Details Dialog
    detailsDialogProduct?.let { product ->
        AlertDialog(
            onDismissRequest = { detailsDialogProduct = null },
            containerColor = O7ASurfaceNavy,
            title = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        O7AStatusBadge(
                            text = product.category.uppercase(),
                            statusType = "INFO"
                        )
                        Text(
                            text = "SKU: ${product.sku}",
                            fontSize = 11.sp,
                            color = O7ATextSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ATextPrimary
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = product.description,
                        fontSize = 13.sp,
                        color = O7ATextSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "COMPATIBLE SCOOTER MODELS:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = O7ANeonCyan
                    )
                    Text(
                        text = product.compatibleModels,
                        fontSize = 12.sp,
                        color = O7ATextPrimary
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Barcode: ${product.barcode}",
                            fontSize = 11.sp,
                            color = O7ATextMuted
                        )
                        Text(
                            text = "₹${product.price.toInt()}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = O7ACyanGlow
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAddToCart(product.id)
                        detailsDialogProduct = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = O7AElectricBlue),
                    modifier = Modifier.testTag("dialog_add_to_cart_button")
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add to Cart")
                }
            },
            dismissButton = {
                TextButton(onClick = { detailsDialogProduct = null }) {
                    Text("Close", color = O7ATextSecondary)
                }
            }
        )
    }
}

@Composable
fun ProductCard(
    product: ProductEntity,
    onAddToCart: () -> Unit,
    onDetailsClick: () -> Unit
) {
    var addedToCartState by remember { mutableStateOf(false) }

    Surface(
        onClick = onDetailsClick,
        shape = RoundedCornerShape(14.dp),
        color = O7ASurfaceNavy,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, O7ACardBorder, RoundedCornerShape(14.dp))
            .testTag("product_card_${product.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.sku,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = O7ANeonCyan
                )

                if (product.stockQty <= 5) {
                    O7AStatusBadge("ONLY ${product.stockQty} LEFT", "WARNING")
                } else {
                    O7AStatusBadge("IN STOCK", "SUCCESS")
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = product.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = O7ATextPrimary,
                lineHeight = 19.sp
            )

            Text(
                text = "Fits: ${product.compatibleModels}",
                fontSize = 11.sp,
                color = O7ATextSecondary,
                maxLines = 1,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹${product.price.toInt()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = O7ACyanGlow
                )

                Button(
                    onClick = {
                        onAddToCart()
                        addedToCartState = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (addedToCartState) O7ASuccessGreen else O7AElectricBlue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("add_to_cart_button_${product.id}")
                ) {
                    Icon(
                        imageVector = if (addedToCartState) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (addedToCartState) "Added" else "Add to Cart",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
