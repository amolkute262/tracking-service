package com.getrosoft.service;

import com.getrosoft.model.TrackingRequest;
import com.getrosoft.model.TrackingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {
    private final ReactiveStringRedisTemplate redisTemplate;
    private final TrackingNumberGenerator generator = new TrackingNumberGenerator();

    public Mono<TrackingResponse> generateTrackingNumber(TrackingRequest request) {
        String key = "counter:" + request.getCustomer_id(); // logical sharding by customer

        return redisTemplate.opsForValue().increment(key)
            .map(counter -> generator.generateTrackingNumber(
                    request.getOrigin_country_id(),
                    request.getDestination_country_id(),
                    request.getCustomer_id(),
                    request.getCustomer_slug(),
                    counter))
            .map(tracking -> new TrackingResponse(tracking, OffsetDateTime.now()));
    }
}