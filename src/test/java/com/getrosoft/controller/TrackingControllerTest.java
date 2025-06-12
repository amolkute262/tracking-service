package com.getrosoft.controller;

import com.getrosoft.model.TrackingRequest;
import com.getrosoft.model.TrackingResponse;
import com.getrosoft.service.TrackingService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;

@WebFluxTest(controllers = TrackingController.class)
class TrackingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TrackingService trackingService;

    @MockBean
    private Validator validator;

    private TrackingRequest request;
    private TrackingResponse response;

    @BeforeEach
    void setUp() {
        request = new TrackingRequest();
        request.setOrigin_country_id("MY");
        request.setCustomer_id(UUID.fromString("de619854-b59b-425e-9db4-943979e1bd49"));
        request.setCustomer_slug("redbox-logistics");
        request.setDestination_country_id("ID");
        request.setWeight(new BigDecimal("1.234"));
        request.setCustomer_name("RedBox Logistics");

        response = new TrackingResponse();
        response.setTracking_number("I16H5QXX5AF8SP00");
    }

    @Test
    void shouldReturnTrackingNumberForValidRequest() {
        when(validator.validate(ArgumentMatchers.any(TrackingRequest.class)))
                .thenReturn(Collections.emptySet());

        when(trackingService.generateTrackingNumber(any(TrackingRequest.class)))
                .thenReturn(Mono.just(response));

        webTestClient.get()
                .uri("/api/next-tracking-number?origin_country_id=MY" +
                        "&customer_id=de619854-b59b-425e-9db4-943979e1bd49" +
                        "&customer_slug=redbox-logistics" +
                        "&destination_country_id=ID" +
                        "&weight=1.234" +
                        "&customer_name=RedBox%20Logistics")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.tracking_number").isEqualTo("I16H5QXX5AF8SP00");

        verify(trackingService, times(1)).generateTrackingNumber(any());
    }

    @Test
    void shouldReturnBadRequestForInvalidRequest() {
        @SuppressWarnings("unchecked")
        ConstraintViolation<TrackingRequest> mockViolation = mock(ConstraintViolation.class);

        // Create a mock Path object to return from getPropertyPath
        Path mockPath = mock(Path.class);
        when(mockPath.toString()).thenReturn("origin_country_id");

        // Mock the violation
        when(mockViolation.getPropertyPath()).thenReturn(mockPath);
        when(mockViolation.getMessage()).thenReturn("Origin country ID is required");

        Set<ConstraintViolation<TrackingRequest>> violations = Set.of(mockViolation);

        when(validator.validate(any(TrackingRequest.class)))
                .thenReturn(violations);

        webTestClient.get()
                .uri("/api/next-tracking-number?origin_country_id=&customer_id=&customer_slug=&destination_country_id=&weight=&customer_name=")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.origin_country_id").isEqualTo("Origin country ID is required");

        verify(trackingService, times(0)).generateTrackingNumber(any());
    }

}
