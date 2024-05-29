package com.zouliga.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "customer firstname is required")
        String firstname,

        @NotNull(message = "customer lastname is required")
        String lastname,

        @NotNull(message = "customer firstname is required")
        String email,
        Address address
) {
}
