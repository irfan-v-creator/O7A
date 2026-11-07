package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sku: String,
    val name: String,
    val category: String, // Brakes, Powertrain, Electrical, Body, Battery, Accessories
    val price: Double,
    val stockQty: Int,
    val isAvailable: Boolean = true,
    val description: String,
    val compatibleModels: String, // Comma separated: "Ola S1 Pro,Ather 450X,TVS iQube"
    val barcode: String,
    val iconType: String = "gear" // brake, battery, motor, display, light, belt, sensor, tool
)

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val make: String, // Ola Electric, Ather Energy, TVS, Bajaj
    val model: String, // S1 Pro Gen 2, 450X Gen 3, iQube ST, Chetak Premium
    val variant: String = "Standard",
    val regNumber: String,
    val vinNumber: String,
    val batteryCapacity: String, // e.g. "4.0 kWh"
    val batteryHealth: Int = 96, // percentage
    val currentOdoKm: Int = 12450,
    val nextServiceKm: Int = 15000,
    val warrantyValidUntil: String = "Nov 2027",
    val isPrimary: Boolean = false
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val orderId: String, // e.g. "O7A-ORD-8942"
    val customerName: String,
    val customerPhone: String,
    val deliveryAddress: String,
    val subtotal: Double,
    val deliveryFee: Double = 50.0,
    val totalAmount: Double,
    val paymentMethod: String = "UPI_QR",
    val paymentStatus: String = "UNDER_REVIEW", // UNPAID, UNDER_REVIEW, VERIFIED, REJECTED
    val utrNumber: String = "",
    val orderStatus: String = "SUBMITTED", // SUBMITTED, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    val createdAt: Long = System.currentTimeMillis(),
    val itemsSummary: String = "" // e.g. "Brake Pad (x2), Drive Belt (x1)"
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productId: Long,
    val quantity: Int
)

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookingReference: String,
    val customerName: String,
    val customerPhone: String,
    val vehicleModel: String,
    val serviceType: String, // General Periodic Service, Brake Overhaul, Software Diagnostics, Battery Health Check
    val centerName: String, // O7A Hub Central, North Valley EV Center, Metro SuperCare
    val appointmentDate: String,
    val timeSlot: String,
    val status: String = "CONFIRMED", // PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "support_tickets")
data class SupportTicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val ticketCode: String,
    val customerName: String,
    val phone: String,
    val department: String, // Higher Technical Support, Warranty & Escalations, Battery Safety Team, Billing
    val vehicleModel: String,
    val issueDescription: String,
    val priority: String = "HIGH", // NORMAL, HIGH, URGENT
    val status: String = "OPEN", // OPEN, IN_INVESTIGATION, RESOLVED
    val createdAt: Long = System.currentTimeMillis()
)
