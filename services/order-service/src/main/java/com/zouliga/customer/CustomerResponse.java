package com.zouliga.customer;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
