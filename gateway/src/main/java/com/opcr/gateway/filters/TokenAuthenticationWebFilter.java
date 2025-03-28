package com.opcr.gateway.filters;

import com.opcr.gateway.services.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

public class TokenAuthenticationWebFilter implements WebFilter {

    private final TokenService tokenService;

    public TokenAuthenticationWebFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain filterChain) {
        String token = extractTokenFromRequest(exchange.getRequest());

        if (token != null && tokenService.isTokenValid(token)) {
            String usernameFromToken = tokenService.extractUsername(token);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            usernameFromToken,
                            null,
                            List.of());
            return filterChain
                    .filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
                            Mono.just(new SecurityContextImpl(authenticationToken))
                    ));
        }
        return filterChain.filter(exchange);
    }


    /**
     * Extract the token from the Request. From header or from cookie.
     *
     * @param request the HttpRequest.
     * @return the token.
     */
    private String extractTokenFromRequest(ServerHttpRequest request) {

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return request.getCookies().getFirst("JWT_TOKEN") != null
                ? request.getCookies().getFirst("JWT_TOKEN").getValue()
                : null;
    }
}
