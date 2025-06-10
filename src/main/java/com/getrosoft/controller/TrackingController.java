package com.getrosoft.controller;

import com.getrosoft.model.TrackingRequest;
import com.getrosoft.model.TrackingResponse;
import com.getrosoft.service.TrackingService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrackingController {
    private final TrackingService trackingService;
    private final Validator validator;

    @GetMapping("/next-tracking-number")
    public Mono<ResponseEntity<TrackingResponse>> getTrackingNumber(@ModelAttribute TrackingRequest request) {
        Set<ConstraintViolation<TrackingRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return trackingService.generateTrackingNumber(request)
                .map(ResponseEntity::ok);
    }
}