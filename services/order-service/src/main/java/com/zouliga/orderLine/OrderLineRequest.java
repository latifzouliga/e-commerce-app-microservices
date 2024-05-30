package com.zouliga.orderLine;

public record OrderLineRequest(
        Integer id,
        Integer OrderId,
        Integer productId,
        double quantity
) {
}
