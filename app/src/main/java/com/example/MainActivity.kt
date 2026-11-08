package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.EVPlatformViewModel
import com.example.ui.components.O7ATopBar
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AppointmentSuccessScreen
import com.example.ui.screens.BookAppointmentScreen
import com.example.ui.screens.CallAndVisitScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CheckoutScreen
import com.example.ui.screens.CustomerHubScreen
import com.example.ui.screens.CustomerProfileScreen
import com.example.ui.screens.DemoVideoScreen
import com.example.ui.screens.MainHubScreen
import com.example.ui.screens.MediaAndZipToolsScreen
import com.example.ui.screens.MonitoringOptionsScreen
import com.example.ui.screens.OrderHistoryScreen
import com.example.ui.screens.OrderSuccessScreen
import com.example.ui.screens.ScannerScreen
import com.example.ui.screens.ServiceCenterHubScreen
import com.example.ui.screens.SoftwareServiceScreen
import com.example.ui.screens.SparePartsHubScreen
import com.example.ui.screens.SparePartsOfflineScreen
import com.example.ui.screens.SparePartsOnlineScreen
import com.example.ui.screens.TransferHigherDeptScreen
import com.example.ui.screens.VehicleGarageScreen
import com.example.ui.screens.VideoAssistantsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.O7ADeepNavy
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = true) {
                O7AApp()
            }
        }
    }
}

@Composable
fun O7AApp(viewModel: EVPlatformViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val primaryVehicle by viewModel.primaryVehicle.collectAsState()
    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val allProducts by viewModel.allProducts.collectAsState()
    val allVehicles by viewModel.allVehicles.collectAsState()
    val allOrders by viewModel.allOrders.collectAsState()
    val allAppointments by viewModel.allAppointments.collectAsState()
    val allTickets by viewModel.allTickets.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val selectedVehicleFilter by viewModel.selectedVehicleFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val scannerResult by viewModel.scannerResult.collectAsState()
    val lastOrder by viewModel.lastCreatedOrder.collectAsState()
    val lastAppointment by viewModel.lastCreatedAppointment.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Handle Android system back gestures smoothly
    BackHandler(enabled = currentScreen != AppScreen.MAIN_HUB) {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            O7ATopBar(
                currentScreen = currentScreen,
                currentRole = currentRole,
                cartCount = cartItems.sumOf { it.cartItem.quantity },
                onBackClick = { viewModel.navigateBack() },
                onHomeClick = { viewModel.navigateHome() },
                onCartClick = { viewModel.navigateTo(AppScreen.CART) },
                onRoleSelect = { viewModel.setRole(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = O7ADeepNavy,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(O7ADeepNavy)
        ) {
            when (currentScreen) {
                AppScreen.MAIN_HUB -> {
                    MainHubScreen(
                        primaryVehicle = primaryVehicle,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                AppScreen.CUSTOMER_HUB -> {
                    CustomerHubScreen(
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                AppScreen.SPARE_PARTS_HUB -> {
                    SparePartsHubScreen(
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                AppScreen.SPARE_PARTS_ONLINE -> {
                    SparePartsOnlineScreen(
                        products = filteredProducts,
                        selectedCategory = selectedCategory,
                        selectedVehicleFilter = selectedVehicleFilter,
                        searchQuery = searchQuery,
                        onCategoryChange = { viewModel.setCategory(it) },
                        onVehicleFilterChange = { viewModel.setVehicleFilter(it) },
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onAddToCart = { productId ->
                            viewModel.addToCart(productId)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Spare part added to cart")
                            }
                        },
                        onNavigateToCart = { viewModel.navigateTo(AppScreen.CART) }
                    )
                }

                AppScreen.SPARE_PARTS_OFFLINE -> {
                    SparePartsOfflineScreen(
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }

                AppScreen.CART -> {
                    CartScreen(
                        cartItems = cartItems,
                        onUpdateQty = { id, qty -> viewModel.updateCartQty(id, qty) },
                        onRemoveItem = { id -> viewModel.removeFromCart(id) },
                        onProceedToCheckout = { viewModel.navigateTo(AppScreen.CHECKOUT) },
                        onBrowseParts = { viewModel.navigateTo(AppScreen.SPARE_PARTS_ONLINE) }
                    )
                }

                AppScreen.CHECKOUT -> {
                    CheckoutScreen(
                        cartItems = cartItems,
                        onPlaceOrder = { name, phone, address, utr ->
                            viewModel.placeOrder(name, phone, address, utr)
                        }
                    )
                }

                AppScreen.ORDER_SUCCESS -> {
                    OrderSuccessScreen(
                        order = lastOrder,
                        onViewOrders = { viewModel.navigateTo(AppScreen.ORDER_HISTORY) },
                        onContinueShopping = { viewModel.navigateTo(AppScreen.SPARE_PARTS_ONLINE) }
                    )
                }

                AppScreen.ORDER_HISTORY -> {
                    OrderHistoryScreen(orders = allOrders)
                }

                AppScreen.SERVICE_CENTER_HUB -> {
                    ServiceCenterHubScreen(
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                AppScreen.BOOK_APPOINTMENT -> {
                    BookAppointmentScreen(
                        vehicles = allVehicles,
                        onBookAppointment = { name, phone, model, type, center, date, slot, notes ->
                            viewModel.bookAppointment(name, phone, model, type, center, date, slot, notes)
                        }
                    )
                }

                AppScreen.APPOINTMENT_SUCCESS -> {
                    AppointmentSuccessScreen(
                        appointment = lastAppointment,
                        onReturnHome = { viewModel.navigateHome() }
                    )
                }

                AppScreen.VEHICLE_GARAGE -> {
                    VehicleGarageScreen(
                        vehicles = allVehicles,
                        onAddVehicle = { make, model, variant, reg, vin, cap ->
                            viewModel.addVehicle(make, model, variant, reg, vin, cap)
                        },
                        onSetPrimary = { viewModel.setPrimaryVehicle(it) }
                    )
                }

                AppScreen.SCANNER -> {
                    ScannerScreen(
                        scannerResult = scannerResult,
                        onScanCode = { viewModel.handleScannedCode(it) },
                        onAddToCart = { productId ->
                            viewModel.addToCart(productId)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Scanned item added to cart")
                            }
                        },
                        onClearResult = { viewModel.clearScannerResult() }
                    )
                }

                AppScreen.VIDEO_ASSISTANTS -> {
                    VideoAssistantsScreen()
                }

                AppScreen.DEMO_VIDEO -> {
                    DemoVideoScreen()
                }

                AppScreen.SOFTWARE_SERVICE -> {
                    SoftwareServiceScreen()
                }

                AppScreen.CALL_AND_VISIT -> {
                    CallAndVisitScreen()
                }

                AppScreen.TRANSFER_HIGHER_DEPT -> {
                    TransferHigherDeptScreen(
                        tickets = allTickets,
                        onSubmitTicket = { name, phone, dept, model, desc, priority ->
                            viewModel.submitSupportTicket(name, phone, dept, model, desc, priority)
                        }
                    )
                }

                AppScreen.MONITORING_OPTIONS -> {
                    MonitoringOptionsScreen()
                }

                AppScreen.CUSTOMER_PROFILE -> {
                    CustomerProfileScreen(
                        profile = userProfile,
                        onSaveProfile = { name, phone, email, address, scooter ->
                            viewModel.updateUserProfile(name, phone, email, address, scooter)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Registration profile updated")
                            }
                        },
                        onToggleLogin = { viewModel.toggleUserLogin() }
                    )
                }

                AppScreen.MEDIA_ZIP_TOOLS -> {
                    MediaAndZipToolsScreen()
                }

                AppScreen.ADMIN_DASHBOARD -> {
                    AdminDashboardScreen(
                        products = allProducts,
                        orders = allOrders,
                        appointments = allAppointments,
                        onUpdateStock = { id, stock -> viewModel.adminUpdateStock(id, stock) },
                        onAddProduct = { viewModel.adminAddProduct(it) },
                        onDeleteProduct = { viewModel.adminDeleteProduct(it) },
                        onVerifyPayment = { orderId, approved -> viewModel.adminVerifyPayment(orderId, approved) },
                        onUpdateOrderStatus = { orderId, status -> viewModel.adminUpdateOrderStatus(orderId, status) },
                        onUpdateAppointmentStatus = { id, status -> viewModel.adminUpdateAppointmentStatus(id, status) }
                    )
                }
            }
        }
    }
}
