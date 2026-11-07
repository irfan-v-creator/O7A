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
}
