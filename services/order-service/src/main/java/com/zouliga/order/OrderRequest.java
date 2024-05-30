package com.zouliga.order;

import com.zouliga.enums.PaymentMethod;
import com.zouliga.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should not be null")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer ID should not be null")
        @NotEmpty(message = "Customer ID should not be null")
        @NotBlank(message = "Customer ID should not be null")
        String customerId,
        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {
}
