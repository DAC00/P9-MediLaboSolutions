package com.opcr.gateway.controllers;

import com.opcr.gateway.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/authenticate")
public class TokenController {

    private final TokenService tokenService;
    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public TokenController(TokenService tokenService, ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<Object>> authenticate(ServerWebExchange exchange, ServerHttpResponse response) {
        return exchange.getFormData()
                .flatMap(data -> {
                    String username = data.getFirst("username");
                    String password = data.getFirst("password");

                    if (username == null || password == null) {
                        return (Mono.just(
                                ResponseEntity.status(HttpStatus.FOUND)
                                        .location(URI.create("/login?errorMessage=Missing%data."))
                                        .build()));
                    }

                    return userDetailsService.findByUsername(username)
                            .flatMap(userDetails -> {
                                if (passwordEncoder.matches(password, userDetails.getPassword())) {
                                    String token = tokenService.generateTheToken(username);

                                    ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
                                            .httpOnly(true)
                                            .path("/")
                                            .maxAge(Duration.ofMinutes(15))
                                            .build();
                                    response.addCookie(cookie);

                                    return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                                            .location(URI.create("/patient/all"))
                                            .build());
                                } else {
                                    return (Mono.just(
                                            ResponseEntity.status(HttpStatus.FOUND)
                                                    .location(URI.create("/login?errorMessage=Error%20password."))
                                                    .build()));
                                }
                            })
                            .switchIfEmpty(Mono.just(
                                    ResponseEntity.status(HttpStatus.FOUND)
                                            .location(URI.create("/login?errorMessage=User%20not%20found."))
                                            .build()));
                });
    }
}
