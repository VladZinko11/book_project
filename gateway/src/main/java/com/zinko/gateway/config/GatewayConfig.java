package com.zinko.gateway.config;

import com.zinko.gateway.data.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Duration;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;


@Configuration
public class GatewayConfig {

    private static final String MY_CIRCUIT_BREAKER = "myCircuitBreaker";

    @Bean
    public RouterFunction<ServerResponse> bookRoute() {
        return route("service-book")
                .route(path("/api/v1/books/**",
                        "/api/v1/authors/**",
                        "/api/v1/series/**",
                        "/api/v1/genres/**"), http())
                .filter(lb("service-book"))
                .filter(circuitBreaker(MY_CIRCUIT_BREAKER))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookMediaRoute() {
        return route("service-media")
                .route(path("/api/v1/media/**"), http())
                .filter(lb("service-media"))
                .filter(circuitBreaker(MY_CIRCUIT_BREAKER))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userRoute() {
        return route("service-user")
                .route(path("/api/v1/users/**",
                        "/api/v1/signing",
                        "/api/v1/signup"), http())
                .filter(lb("service-user"))
                .filter(circuitBreaker(MY_CIRCUIT_BREAKER))
                .build();
    }


    @Bean
    public CircuitBreaker myCircuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slowCallRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slowCallDurationThreshold(Duration.ofSeconds(2))
                .permittedNumberOfCallsInHalfOpenState(3)
                .minimumNumberOfCalls(10)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .slidingWindowSize(5)
                .build();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        return circuitBreakerRegistry
                .circuitBreaker(MY_CIRCUIT_BREAKER, circuitBreakerConfig);
    }

    @Bean
    public UserDetailsService myUserDetailsService(UserRepository userRepository) {
        return userRepository::findByEmail;
    }
}
