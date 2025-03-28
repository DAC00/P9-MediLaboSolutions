package com.opcr.gateway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.net.URI;

@Controller
public class LogoutController {

    /**
     * Expire the cookie, disconnect the User and redirect to /login.
     *
     * @param response of http server.
     * @return redirect the User.
     */
    @GetMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(ServerHttpResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("JWT_TOKEN", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        response.addCookie(deleteCookie);

        return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/login?message=Logout%20completed."))
                .build());
    }
}
