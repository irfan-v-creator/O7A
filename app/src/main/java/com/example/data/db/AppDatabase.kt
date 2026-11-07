package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.AppointmentDao
import com.example.data.dao.CartDao
import com.example.data.dao.OrderDao
import com.example.data.dao.ProductDao
import com.example.data.dao.SupportDao
import com.example.data.dao.VehicleDao
import com.example.data.model.AppointmentEntity
import com.example.data.model.CartItemEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.SupportTicketEntity
import com.example.data.model.VehicleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ProductEntity::class,
        VehicleEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        AppointmentEntity::class,
        SupportTicketEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun supportDao(): SupportDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "o7a_ev_platform.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed default data
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            seedInitialData(database)
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun seedInitialData(database: AppDatabase) {
            val initialProducts = listOf(
                ProductEntity(
                    sku = "O7A-BRK-D01",
                    name = "Front Ceramic Disc Brake Pads Set",
                    category = "Brakes",
                    price = 480.0,
                    stockQty = 28,
                    isAvailable = true,
                    description = "High-friction heat-resistant ceramic composite brake pads designed for regenerative and hydraulic braking systems.",
                    compatibleModels = "Ola S1,Ola S1 Pro,Ather 450X,TVS iQube",
                    barcode = "8901234567890",
                    iconType = "brake"
                ),
                ProductEntity(
                    sku = "O7A-BRK-R02",
                    name = "Rear Hydraulic Brake Caliper Assembly",
                    category = "Brakes",
                    price = 1850.0,
                    stockQty = 12,
                    isAvailable = true,
                    description = "OEM specification rear dual-piston hydraulic caliper with pre-installed bleed screw and fluid line connector.",
                    compatibleModels = "Ola S1 Pro,Ather 450X Gen 3",
                    barcode = "8901234567891",
                    iconType = "brake"
                ),
                ProductEntity(
                    sku = "O7A-PWR-B01",
                    name = "Carbon Fiber Reinforced Final Drive Belt",
                    category = "Powertrain",
                    price = 2200.0,
                    stockQty = 15,
                    isAvailable = true,
                    description = "High-tensile carbon tensile cord drive belt minimizing power loss and operating with whisper-quiet transmission acoustics.",
                    compatibleModels = "Ather 450X,Ather 450S,Ola S1 Pro",
                    barcode = "8901234567892",
                    iconType = "belt"
                ),
                ProductEntity(
                    sku = "O7A-PWR-SP1",
                    name = "Lightweight Alloy Rear Drive Sprocket 54T",
                    category = "Powertrain",
                    price = 1450.0,
                    stockQty = 20,
                    isAvailable = true,
                    description = "CNC-machined hard-anodized 7075-T6 alloy rear pulley sprocket engineered for long service life and instant EV torque delivery.",
                    compatibleModels = "Ola S1 Pro,Ola S1 Air,Ather 450X",
                    barcode = "8901234567893",
                    iconType = "gear"
                ),
                ProductEntity(
                    sku = "O7A-ELC-DSP",
                    name = "7-Inch TFT Smart Instrument Cluster Display",
                    category = "Electrical",
                    price = 7800.0,
                    stockQty = 6,
                    isAvailable = true,
                    description = "IP67 weather-sealed capacitive multi-touch digital display cluster with integrated Bluetooth and LTE telemetry controller.",
                    compatibleModels = "Ola S1 Pro Gen 2,Ola S1",
                    barcode = "8901234567894",
                    iconType = "display"
                ),
                ProductEntity(
                    sku = "O7A-ELC-SWL",
                    name = "Handlebar Switchgear Module Left (Indicators/Horn)",
                    category = "Electrical",
                    price = 1250.0,
                    stockQty = 18,
                    isAvailable = true,
                    description = "Sealed tactical switch assembly for reverse toggle, high/low beam, hazard flasher, and high-decibel electric horn.",
                    compatibleModels = "Ola S1,Ola S1 Pro,TVS iQube,Bajaj Chetak",
                    barcode = "8901234567895",
                    iconType = "switch"
                ),
                ProductEntity(
                    sku = "O7A-BAT-CHG",
                    name = "Portable Fast Home Charger 750W",
                    category = "Battery",
                    price = 4500.0,
                    stockQty = 14,
                    isAvailable = true,
                    description = "Smart regulated CC/CV home charger unit featuring auto-cutoff, short-circuit surge protection, and IP65 ruggedized casing.",
                    compatibleModels = "Ola S1,Ola S1 Pro,Ather 450X,TVS iQube,Bajaj Chetak",
                    barcode = "8901234567896",
                    iconType = "battery"
                ),
                ProductEntity(
                    sku = "O7A-BAT-BMS",
                    name = "Smart Battery Management System (BMS) Sensor Harness",
                    category = "Battery",
                    price = 1950.0,
                    stockQty = 9,
                    isAvailable = true,
                    description = "Multi-channel thermal probe and cell balancing wiring loom for 16S / 20S lithium-ion EV battery packs.",
                    compatibleModels = "Ola S1 Pro,Ather 450X",
                    barcode = "8901234567897",
                    iconType = "sensor"
                ),
                ProductEntity(
                    sku = "O7A-BDY-MRR",
                    name = "Aerodynamic Bar-End Rear View Mirror Pair",
                    category = "Body",
                    price = 890.0,
                    stockQty = 35,
                    isAvailable = true,
                    description = "Convex anti-glare blue-tinted tempered glass mirrors with billet aluminum clamps and 360-degree ball pivot.",
                    compatibleModels = "Ola S1,Ola S1 Pro,Ather 450X,TVS iQube,Bajaj Chetak",
                    barcode = "8901234567898",
                    iconType = "mirror"
                ),
                ProductEntity(
                    sku = "O7A-BDY-SUS",
                    name = "Monoshock Rear Gas-Charged Suspension Damper",
                    category = "Body",
                    price = 3200.0,
                    stockQty = 8,
                    isAvailable = true,
                    description = "Preload-adjustable progressive-rate nitrogen charged monoshock designed for smooth city comfort and dual passenger loads.",
                    compatibleModels = "Ola S1 Pro,Ola S1 Air,TVS iQube",
                    barcode = "8901234567899",
                    iconType = "tool"
                ),
                ProductEntity(
                    sku = "O7A-ACC-MAT",
                    name = "All-Weather Heavy Duty Floor Mat",
                    category = "Accessories",
                    price = 650.0,
                    stockQty = 42,
                    isAvailable = true,
                    description = "Custom-molded textured silicone anti-skid floorboard liner with raised spill channels and wash-clean drainage holes.",
                    compatibleModels = "Ola S1,Ola S1 Pro Gen 2",
                    barcode = "8901234567801",
                    iconType = "gear"
                ),
                ProductEntity(
                    sku = "O7A-ACC-GRD",
                    name = "Stainless Steel Perimeter Crash Guard Set",
                    category = "Accessories",
                    price = 2490.0,
                    stockQty = 16,
                    isAvailable = true,
                    description = "Grade 304 powder-coated stainless steel wrap-around protective tubing safeguarding side panels and pillion footrests.",
                    compatibleModels = "Ola S1,Ola S1 Pro,TVS iQube",
                    barcode = "8901234567802",
                    iconType = "tool"
                )
            )
            database.productDao().insertProducts(initialProducts)

            val initialVehicles = listOf(
                VehicleEntity(
                    make = "Ola Electric",
                    model = "S1 Pro Gen 2",
                    variant = "Performance Edition",
                    regNumber = "KL-07-CS-4091",
                    vinNumber = "MD9EV2410S198217",
                    batteryCapacity = "4.0 kWh",
                    batteryHealth = 97,
                    currentOdoKm = 11840,
                    nextServiceKm = 15000,
                    warrantyValidUntil = "Oct 2027",
                    isPrimary = true
                ),
                VehicleEntity(
                    make = "Ather Energy",
                    model = "450X Gen 3",
                    variant = "Pro Warp",
                    regNumber = "KL-01-BK-2034",
                    vinNumber = "ME8ATH450G300189",
                    batteryCapacity = "3.7 kWh",
                    batteryHealth = 93,
                    currentOdoKm = 18620,
                    nextServiceKm = 20000,
                    warrantyValidUntil = "Mar 2026",
                    isPrimary = false
                )
            )
            database.vehicleDao().insertVehicles(initialVehicles)

            val initialOrders = listOf(
                OrderEntity(
                    orderId = "O7A-ORD-8942",
                    customerName = "Rahul Nair",
                    customerPhone = "+91 98470 12345",
                    deliveryAddress = "Flat 4B, Skyline Ivy, Kakkanad, Kochi, Kerala - 682030",
                    subtotal = 2680.0,
                    deliveryFee = 50.0,
                    totalAmount = 2730.0,
                    paymentMethod = "UPI_QR",
                    paymentStatus = "VERIFIED",
                    utrNumber = "431289017264",
                    orderStatus = "PROCESSING",
                    itemsSummary = "Front Ceramic Disc Brake Pads Set (x1), Carbon Fiber Drive Belt (x1)"
                ),
                OrderEntity(
                    orderId = "O7A-ORD-9105",
                    customerName = "Rahul Nair",
                    customerPhone = "+91 98470 12345",
                    deliveryAddress = "Flat 4B, Skyline Ivy, Kakkanad, Kochi, Kerala - 682030",
                    subtotal = 650.0,
                    deliveryFee = 50.0,
                    totalAmount = 700.0,
                    paymentMethod = "UPI_QR",
                    paymentStatus = "UNDER_REVIEW",
                    utrNumber = "510928374619",
                    orderStatus = "SUBMITTED",
                    itemsSummary = "All-Weather Heavy Duty Floor Mat (x1)"
                )
            )
            initialOrders.forEach { database.orderDao().insertOrder(it) }

            val initialAppointments = listOf(
                AppointmentEntity(
                    bookingReference = "O7A-SRV-2081",
                    customerName = "Rahul Nair",
                    customerPhone = "+91 98470 12345",
                    vehicleModel = "Ola S1 Pro Gen 2",
                    serviceType = "General Periodic Service (15,000 km)",
                    centerName = "O7A Central Hub, Metro EV Center, Kochi",
                    appointmentDate = "Tomorrow, 10:30 AM",
                    timeSlot = "10:30 AM - 12:30 PM",
                    status = "CONFIRMED",
                    notes = "Check rear brake bite and perform software diagnostics."
                )
            )
            initialAppointments.forEach { database.appointmentDao().insertAppointment(it) }
        }
    }
}
