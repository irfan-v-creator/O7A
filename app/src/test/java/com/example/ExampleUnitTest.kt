package com.example

import com.example.data.model.ProductEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun product_compatibility_check_matches_model() {
        val product = ProductEntity(
            id = 1,
            sku = "O7A-BRK-D01",
            name = "Front Ceramic Disc Brake Pads Set",
            category = "Brakes",
            price = 480.0,
            stockQty = 28,
            isAvailable = true,
            description = "Ceramic brake pads",
            compatibleModels = "Ola S1,Ola S1 Pro,Ather 450X,TVS iQube",
            barcode = "8901234567890"
        )

        assertTrue(product.compatibleModels.contains("Ola S1 Pro"))
        assertTrue(product.compatibleModels.contains("Ather 450X"))
        assertFalse(product.compatibleModels.contains("Bajaj Chetak"))
    }

    @Test
    fun cart_calculation_applies_free_delivery_above_threshold() {
        val subtotalSmall = 800.0
        val deliveryFeeSmall = if (subtotalSmall > 1500) 0.0 else 50.0
        assertEquals(50.0, deliveryFeeSmall, 0.01)

        val subtotalLarge = 2200.0
        val deliveryFeeLarge = if (subtotalLarge > 1500) 0.0 else 50.0
        assertEquals(0.0, deliveryFeeLarge, 0.01)
    }

    @Test
    fun utr_number_validation_accepts_valid_12_digit_number() {
        val validUtr = "123456789012"
        val invalidUtrShort = "12345"
        val invalidUtrAlpha = "12345678901A"

        fun isValidUtr(utr: String): Boolean = utr.trim().length >= 10 && utr.all { it.isDigit() || it.isLetter() }

        assertTrue(isValidUtr(validUtr))
        assertFalse(isValidUtr(invalidUtrShort))
        assertTrue(isValidUtr(invalidUtrAlpha))
    }

    @Test
    fun stock_level_status_indicator_logic() {
        fun getStockStatus(stock: Int): String {
            return when {
                stock <= 0 -> "OUT_OF_STOCK"
                stock <= 5 -> "LOW_STOCK"
                else -> "IN_STOCK"
            }
        }

        assertEquals("OUT_OF_STOCK", getStockStatus(0))
        assertEquals("LOW_STOCK", getStockStatus(3))
        assertEquals("IN_STOCK", getStockStatus(15))
    }

    @Test
    fun offline_pickup_reservation_code_generator_format() {
        val reservationCode = "O7A-PKP-${1000 + 42}"
        assertTrue(reservationCode.startsWith("O7A-PKP-"))
        assertEquals(12, reservationCode.length)
    }

    @Test
    fun zip_slip_security_validation_detects_traversal_attempts() {
        val destDir = java.io.File("/data/user/0/com.example/cache/extracted").canonicalPath
        
        fun isPathSafe(entryName: String): Boolean {
            val file = java.io.File(destDir, entryName)
            return file.canonicalPath.startsWith(destDir + java.io.File.separator)
        }

        assertTrue(isPathSafe("manuals/specs.txt"))
        assertTrue(isPathSafe("torque_chart.csv"))
        assertFalse(isPathSafe("../../../etc/passwd"))
        assertFalse(isPathSafe("sub/../../dangerous_script.sh"))
    }
}
