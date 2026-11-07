package com.example.data.repository

import com.example.data.db.AppDatabase
import com.example.data.model.AppointmentEntity
import com.example.data.model.CartItemEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.SupportTicketEntity
import com.example.data.model.VehicleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlin.random.Random

data class CartItemWithProduct(
    val cartItem: CartItemEntity,
    val product: ProductEntity
)

class EVRepository(private val database: AppDatabase) {

    // --- PRODUCTS ---
    fun getAllProducts(): Flow<List<ProductEntity>> = database.productDao().getAllProducts()

    fun getProductsByCategory(category: String): Flow<List<ProductEntity>> {
        return if (category == "All") {
            database.productDao().getAllProducts()
        } else {
            database.productDao().getProductsByCategory(category)
        }
    }

    suspend fun getProductById(id: Long): ProductEntity? = database.productDao().getProductById(id)

    suspend fun getProductByBarcode(code: String): ProductEntity? = database.productDao().getProductByBarcode(code)

    suspend fun updateStock(productId: Long, newStock: Int) {
        database.productDao().updateStock(productId, newStock)
    }

    suspend fun updateProductPrice(productId: Long, price: Double, isAvailable: Boolean) {
        database.productDao().updatePriceAndAvailability(productId, price, isAvailable)
    }

    suspend fun addProduct(product: ProductEntity): Long {
        return database.productDao().insertProduct(product)
    }

    // --- VEHICLES (GARAGE) ---
    fun getAllVehicles(): Flow<List<VehicleEntity>> = database.vehicleDao().getAllVehicles()

    fun getPrimaryVehicle(): Flow<VehicleEntity?> = database.vehicleDao().getPrimaryVehicle()

    suspend fun addVehicle(vehicle: VehicleEntity): Long {
        return database.vehicleDao().insertVehicle(vehicle)
    }

    suspend fun setPrimaryVehicle(vehicleId: Long) {
        database.vehicleDao().clearPrimaryFlag()
        database.vehicleDao().setPrimary(vehicleId)
    }

    // --- CART ---
    val cartItemsWithProducts: Flow<List<CartItemWithProduct>> =
        combine(database.cartDao().getCartItems(), database.productDao().getAllProducts()) { cartItems, products ->
            val productMap = products.associateBy { it.id }
            cartItems.mapNotNull { cartItem ->
                productMap[cartItem.productId]?.let { product ->
                    CartItemWithProduct(cartItem, product)
                }
            }
        }

    suspend fun addToCart(productId: Long, quantity: Int = 1) {
        val existing = database.cartDao().getItemForProduct(productId)
        if (existing != null) {
            database.cartDao().updateQuantity(existing.id, existing.quantity + quantity)
        } else {
            database.cartDao().insertCartItem(CartItemEntity(productId = productId, quantity = quantity))
        }
    }

    suspend fun updateCartQuantity(cartItemId: Long, newQuantity: Int) {
        if (newQuantity <= 0) {
            database.cartDao().updateQuantity(cartItemId, 0)
        } else {
            database.cartDao().updateQuantity(cartItemId, newQuantity)
        }
    }

    suspend fun removeFromCart(cartItemId: Long) {
        database.cartDao().deleteCartItem(CartItemEntity(id = cartItemId, productId = 0, quantity = 0))
    }

    suspend fun clearCart() {
        database.cartDao().clearCart()
    }

    // --- ORDERS & UPI CHECKOUT ---
    fun getAllOrders(): Flow<List<OrderEntity>> = database.orderDao().getAllOrders()

    suspend fun createOrder(
        customerName: String,
        customerPhone: String,
        address: String,
        items: List<CartItemWithProduct>,
        utrNumber: String
    ): OrderEntity {
        val subtotal = items.sumOf { it.product.price * it.cartItem.quantity }
        val deliveryFee = if (subtotal > 1500) 0.0 else 50.0
        val total = subtotal + deliveryFee
        val orderCode = "O7A-ORD-${Random.nextInt(1000, 9999)}"
        val summary = items.joinToString(", ") { "${it.product.name} (x${it.cartItem.quantity})" }

        val order = OrderEntity(
            orderId = orderCode,
            customerName = customerName,
            customerPhone = customerPhone,
            deliveryAddress = address,
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            totalAmount = total,
            paymentMethod = "UPI_QR",
            paymentStatus = if (utrNumber.isNotBlank()) "UNDER_REVIEW" else "UNPAID",
            utrNumber = utrNumber,
            orderStatus = "SUBMITTED",
            itemsSummary = summary
        )

        database.orderDao().insertOrder(order)

        // Deduct inventory stock safely
        items.forEach { item ->
            val remainingStock = maxOf(0, item.product.stockQty - item.cartItem.quantity)
            database.productDao().updateStock(item.product.id, remainingStock)
        }

        // Empty cart
        database.cartDao().clearCart()

        return order
    }

    suspend fun updateOrderStatus(orderId: String, status: String) {
        database.orderDao().updateOrderStatus(orderId, status)
    }

    suspend fun verifyPayment(orderId: String, isApproved: Boolean) {
        val newPaymentStatus = if (isApproved) "VERIFIED" else "REJECTED"
        val order = database.orderDao().getOrderById(orderId)
        val utr = order?.utrNumber ?: ""
        database.orderDao().updatePaymentStatus(orderId, newPaymentStatus, utr)
        if (isApproved) {
            database.orderDao().updateOrderStatus(orderId, "CONFIRMED")
        }
    }

    // --- APPOINTMENTS (SERVICE CENTER) ---
    fun getAllAppointments(): Flow<List<AppointmentEntity>> = database.appointmentDao().getAllAppointments()

    suspend fun bookAppointment(
        customerName: String,
        phone: String,
        vehicleModel: String,
        serviceType: String,
        centerName: String,
        date: String,
        timeSlot: String,
        notes: String
    ): AppointmentEntity {
        val bookingRef = "O7A-SRV-${Random.nextInt(1000, 9999)}"
        val appointment = AppointmentEntity(
            bookingReference = bookingRef,
            customerName = customerName,
            customerPhone = phone,
            vehicleModel = vehicleModel,
            serviceType = serviceType,
            centerName = centerName,
            appointmentDate = date,
            timeSlot = timeSlot,
            notes = notes,
            status = "CONFIRMED"
        )
        database.appointmentDao().insertAppointment(appointment)
        return appointment
    }

    suspend fun updateAppointmentStatus(appointmentId: Long, status: String) {
        database.appointmentDao().updateStatus(appointmentId, status)
    }

    // --- SUPPORT / ESCALATIONS ---
    fun getAllSupportTickets(): Flow<List<SupportTicketEntity>> = database.supportDao().getAllTickets()

    suspend fun createSupportTicket(
        customerName: String,
        phone: String,
        department: String,
        vehicleModel: String,
        issueDescription: String,
        priority: String
    ): SupportTicketEntity {
        val ticket = SupportTicketEntity(
            ticketCode = "O7A-ESC-${Random.nextInt(100, 999)}",
            customerName = customerName,
            phone = phone,
            department = department,
            vehicleModel = vehicleModel,
            issueDescription = issueDescription,
            priority = priority,
            status = "OPEN"
        )
        database.supportDao().insertTicket(ticket)
        return ticket
    }

    // Initialize check for initial seed data
    suspend fun ensureSeeded() {
        if (database.productDao().getProductCount() == 0) {
            AppDatabase.seedInitialData(database)
        }
    }
}
