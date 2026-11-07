package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AppointmentEntity
import com.example.data.model.CartItemEntity
import com.example.data.model.OrderEntity
import com.example.data.model.ProductEntity
import com.example.data.model.SupportTicketEntity
import com.example.data.model.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): ProductEntity?

    @Query("SELECT * FROM products WHERE barcode = :barcode OR sku = :barcode LIMIT 1")
    suspend fun getProductByBarcode(barcode: String): ProductEntity?

    @Query("SELECT * FROM products WHERE category = :category ORDER BY name ASC")
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>

    @Query("UPDATE products SET stockQty = :newStock WHERE id = :productId")
    suspend fun updateStock(productId: Long, newStock: Int)

    @Query("UPDATE products SET price = :newPrice, isAvailable = :isAvailable WHERE id = :productId")
    suspend fun updatePriceAndAvailability(productId: Long, newPrice: Double, isAvailable: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
}

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles ORDER BY isPrimary DESC, id DESC")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    suspend fun getVehicleById(id: Long): VehicleEntity?

    @Query("SELECT * FROM vehicles WHERE isPrimary = 1 LIMIT 1")
    fun getPrimaryVehicle(): Flow<VehicleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: VehicleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicles(vehicles: List<VehicleEntity>)

    @Query("UPDATE vehicles SET isPrimary = 0")
    suspend fun clearPrimaryFlag()

    @Query("UPDATE vehicles SET isPrimary = 1 WHERE id = :id")
    suspend fun setPrimary(id: Long)

    @Query("SELECT COUNT(*) FROM vehicles")
    suspend fun getVehicleCount(): Int
}

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getItemForProduct(productId: Long): CartItemEntity?

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Long, quantity: Int)

    @Delete
    suspend fun deleteCartItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Long)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    suspend fun getOrderById(orderId: String): OrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("UPDATE orders SET orderStatus = :status WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: String, status: String)

    @Query("UPDATE orders SET paymentStatus = :paymentStatus, utrNumber = :utr WHERE orderId = :orderId")
    suspend fun updatePaymentStatus(orderId: String, paymentStatus: String, utr: String)

    @Query("SELECT COUNT(*) FROM orders")
    suspend fun getOrderCount(): Int
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY createdAt DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity): Long

    @Query("UPDATE appointments SET status = :newStatus WHERE id = :id")
    suspend fun updateStatus(id: Long, newStatus: String)

    @Query("SELECT COUNT(*) FROM appointments")
    suspend fun getAppointmentCount(): Int
}

@Dao
interface SupportDao {
    @Query("SELECT * FROM support_tickets ORDER BY createdAt DESC")
    fun getAllTickets(): Flow<List<SupportTicketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicketEntity): Long

    @Query("UPDATE support_tickets SET status = :status WHERE id = :id")
    suspend fun updateTicketStatus(id: Long, status: String)
}
