package com.zouliga.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(

        @NotNull(message = "Product is required")
        Integer productId,
        @NotNull(message = "Quantity is required")
        double quantity

) {
}
