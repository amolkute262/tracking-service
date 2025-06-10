package com.getrosoft.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingRequest {

    @NotBlank(message = "Origin country ID is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Origin country code must be ISO alpha-2")
    private String origin_country_id;

    @NotBlank(message = "Destination country ID is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Destination country code must be ISO alpha-2")
    private String destination_country_id;

    @DecimalMin(value = "0.001", message = "Weight must be >= 0.001")
    @Digits(integer = 5, fraction = 3, message = "Max 3 decimal places allowed")
    private BigDecimal weight;

    @NotNull(message = "Customer ID is required")
    private UUID customer_id;

    @NotBlank(message = "Customer name is required")
    private String customer_name;

    @NotBlank(message = "Customer slug is required")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Customer slug must be kebab-case")
    private String customer_slug;
}