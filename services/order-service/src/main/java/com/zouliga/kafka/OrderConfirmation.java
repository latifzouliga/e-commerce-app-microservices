package com.zouliga.kafka;

import com.zouliga.customer.CustomerResponse;
import com.zouliga.enums.PaymentMethod;
import com.zouliga.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
