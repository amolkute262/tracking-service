package com.getrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class TrackingResponse {
    private String tracking_number;
    private OffsetDateTime created_at;
}