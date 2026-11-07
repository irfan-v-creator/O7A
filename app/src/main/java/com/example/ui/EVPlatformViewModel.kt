package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.AppointmentEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.SupportTicketEntity
import com.example.data.model.VehicleEntity
import com.example.data.repository.CartItemWithProduct
import com.example.data.repository.EVRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    MAIN_HUB,
    CUSTOMER_HUB,
    SPARE_PARTS_HUB,
    SPARE_PARTS_ONLINE,
    SPARE_PARTS_OFFLINE,
    CART,
    CHECKOUT,
    ORDER_SUCCESS,
    ORDER_HISTORY,
    SERVICE_CENTER_HUB,
    BOOK_APPOINTMENT,
    APPOINTMENT_SUCCESS,
    VEHICLE_GARAGE,
    SCANNER,
    VIDEO_ASSISTANTS,
    DEMO_VIDEO,
    SOFTWARE_SERVICE,
    CALL_AND_VISIT,
    TRANSFER_HIGHER_DEPT,
    MONITORING_OPTIONS,
    ADMIN_DASHBOARD
}

enum class UserRole(val label: String) {
    CUSTOMER("Customer"),
    SERVICE_STAFF("Service Center"),
    ADMIN("Admin / Inventory")
}

data class ScannerResult(
    val code: String,
    val matchedProduct: ProductEntity? = null,
    val matchedVehicleVin: String? = null,
    val message: String
)

class EVPlatformViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EVRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = EVRepository(db)
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    // Navigation Backstack
    private val _screenStack = MutableStateFlow(listOf(AppScreen.MAIN_HUB))
    val currentScreen: StateFlow<AppScreen> = MutableStateFlow(AppScreen.MAIN_HUB).also { state ->
        viewModelScope.launch {
            _screenStack.collect { stack ->
                state.value = stack.lastOrNull() ?: AppScreen.MAIN_HUB
            }
        }
    }

    // Active Role
    private val _currentRole = MutableStateFlow(UserRole.CUSTOMER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Filter & Search states
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedVehicleFilter = MutableStateFlow("All")
    val selectedVehicleFilter: StateFlow<String> = _selectedVehicleFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Dialog / Sheet states
    private val _selectedProduct = MutableStateFlow<ProductEntity?>(null)
    val selectedProduct: StateFlow<ProductEntity?> = _selectedProduct.asStateFlow()

    private val _scannerResult = MutableStateFlow<ScannerResult?>(null)
    val scannerResult: StateFlow<ScannerResult?> = _scannerResult.asStateFlow()

    private val _lastCreatedOrder = MutableStateFlow<OrderEntity?>(null)
    val lastCreatedOrder: StateFlow<OrderEntity?> = _lastCreatedOrder.asStateFlow()

    private val _lastCreatedAppointment = MutableStateFlow<AppointmentEntity?>(null)
    val lastCreatedAppointment: StateFlow<AppointmentEntity?> = _lastCreatedAppointment.asStateFlow()

    private val _lastCreatedTicket = MutableStateFlow<SupportTicketEntity?>(null)
    val lastCreatedTicket: StateFlow<SupportTicketEntity?> = _lastCreatedTicket.asStateFlow()

    // Data streams
    val allProducts: StateFlow<List<ProductEntity>> = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredProducts: StateFlow<List<ProductEntity>> = combine(
        allProducts,
        _selectedCategory,
        _selectedVehicleFilter,
        _searchQuery
    ) { products, category, vehicleFilter, query ->
        products.filter { product ->
            val matchesCategory = (category == "All") || (product.category.equals(category, ignoreCase = true))
            val matchesVehicle = (vehicleFilter == "All") || (product.compatibleModels.contains(vehicleFilter, ignoreCase = true))
            val matchesQuery = query.isBlank() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.sku.contains(query, ignoreCase = true) ||
                    product.compatibleModels.contains(query, ignoreCase = true)
            matchesCategory && matchesVehicle && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allVehicles: StateFlow<List<VehicleEntity>> = repository.getAllVehicles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val primaryVehicle: StateFlow<VehicleEntity?> = repository.getPrimaryVehicle()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val cartItems: StateFlow<List<CartItemWithProduct>> = repository.cartItemsWithProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allOrders: StateFlow<List<OrderEntity>> = repository.getAllOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allAppointments: StateFlow<List<AppointmentEntity>> = repository.getAllAppointments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTickets: StateFlow<List<SupportTicketEntity>> = repository.getAllSupportTickets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- NAVIGATION ---
    fun navigateTo(screen: AppScreen) {
        val current = _screenStack.value
        if (current.lastOrNull() != screen) {
            _screenStack.value = current + screen
        }
    }

    fun navigateBack(): Boolean {
        val current = _screenStack.value
        return if (current.size > 1) {
            _screenStack.value = current.dropLast(1)
            true
        } else {
            false
        }
    }

    fun navigateHome() {
        _screenStack.value = listOf(AppScreen.MAIN_HUB)
    }

    fun setRole(role: UserRole) {
        _currentRole.value = role
        if (role == UserRole.ADMIN && currentScreen.value != AppScreen.ADMIN_DASHBOARD) {
            navigateTo(AppScreen.ADMIN_DASHBOARD)
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setVehicleFilter(vehicleModel: String) {
        _selectedVehicleFilter.value = vehicleModel
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectProduct(product: ProductEntity?) {
        _selectedProduct.value = product
    }

    fun clearScannerResult() {
        _scannerResult.value = null
    }

    // --- CART ACTIONS ---
    fun addToCart(productId: Long, qty: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(productId, qty)
        }
    }

    fun updateCartQty(cartItemId: Long, newQty: Int) {
        viewModelScope.launch {
            if (newQty <= 0) {
                repository.removeFromCart(cartItemId)
            } else {
                repository.updateCartQuantity(cartItemId, newQty)
            }
        }
    }

    fun removeFromCart(cartItemId: Long) {
        viewModelScope.launch {
            repository.removeFromCart(cartItemId)
        }
    }

    // --- CHECKOUT & ORDER ---
    fun placeOrder(customerName: String, phone: String, address: String, utrNumber: String) {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isNotEmpty()) {
                val order = repository.createOrder(customerName, phone, address, items, utrNumber)
                _lastCreatedOrder.value = order
                navigateTo(AppScreen.ORDER_SUCCESS)
            }
        }
    }

    // --- APPOINTMENTS ---
    fun bookAppointment(
        name: String,
        phone: String,
        vehicleModel: String,
        serviceType: String,
        centerName: String,
        date: String,
        timeSlot: String,
        notes: String
    ) {
        viewModelScope.launch {
            val appointment = repository.bookAppointment(
                customerName = name,
                phone = phone,
                vehicleModel = vehicleModel,
                serviceType = serviceType,
                centerName = centerName,
                date = date,
                timeSlot = timeSlot,
                notes = notes
            )
            _lastCreatedAppointment.value = appointment
            navigateTo(AppScreen.APPOINTMENT_SUCCESS)
        }
    }

    // --- VEHICLE GARAGE ---
    fun addVehicle(
        make: String,
        model: String,
        variant: String,
        regNumber: String,
        vinNumber: String,
        batteryCapacity: String
    ) {
        viewModelScope.launch {
            val vehicle = VehicleEntity(
                make = make,
                model = model,
                variant = variant,
                regNumber = regNumber,
                vinNumber = vinNumber,
                batteryCapacity = batteryCapacity,
                batteryHealth = 98,
                currentOdoKm = 100,
                nextServiceKm = 5000,
                warrantyValidUntil = "Nov 2028",
                isPrimary = false
            )
            repository.addVehicle(vehicle)
        }
    }

    fun setPrimaryVehicle(id: Long) {
        viewModelScope.launch {
            repository.setPrimaryVehicle(id)
        }
    }

    // --- SCANNER ACTION ---
    fun handleScannedCode(code: String) {
        viewModelScope.launch {
            val matchedProduct = repository.getProductByBarcode(code)
            if (matchedProduct != null) {
                _scannerResult.value = ScannerResult(
                    code = code,
                    matchedProduct = matchedProduct,
                    message = "Matched Genuine EV Spare Part: ${matchedProduct.name}"
                )
            } else if (code.startsWith("MD9EV") || code.startsWith("ME8ATH") || code.startsWith("KL-")) {
                _scannerResult.value = ScannerResult(
                    code = code,
                    matchedVehicleVin = code,
                    message = "Matched EV Vehicle Identifier / VIN: $code"
                )
            } else {
                _scannerResult.value = ScannerResult(
                    code = code,
                    message = "Code scanned: $code. No exact product match found in local catalog."
                )
            }
        }
    }

    // --- SUPPORT / ESCALATION ---
    fun submitSupportTicket(
        name: String,
        phone: String,
        dept: String,
        model: String,
        description: String,
        priority: String
    ) {
        viewModelScope.launch {
            val ticket = repository.createSupportTicket(name, phone, dept, model, description, priority)
            _lastCreatedTicket.value = ticket
        }
    }

    // --- ADMIN ACTIONS ---
    fun adminUpdateStock(productId: Long, newStock: Int) {
        viewModelScope.launch {
            repository.updateStock(productId, newStock)
        }
    }

    fun adminVerifyPayment(orderId: String, isApproved: Boolean) {
        viewModelScope.launch {
            repository.verifyPayment(orderId, isApproved)
        }
    }

    fun adminUpdateOrderStatus(orderId: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
        }
    }

    fun adminUpdateAppointmentStatus(appointmentId: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, newStatus)
        }
    }
}
