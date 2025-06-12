package com.getrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingResponse {
    private String tracking_number;
    private OffsetDateTime created_at;
}