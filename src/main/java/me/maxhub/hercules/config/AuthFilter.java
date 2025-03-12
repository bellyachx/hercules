package me.maxhub.hercules.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.service.kc.KeycloakService;
import org.keycloak.common.VerificationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final KeycloakService keycloakService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var existingSession = request.getSession(false);
        if (Objects.nonNull(existingSession) && Objects.nonNull(existingSession.getAttribute("userId"))) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            var jwt = authorizationHeader.substring(7);
            try {
                var token = keycloakService.verifyToken(jwt);
                var subjectId = token.getSubject();

                var user = keycloakService.getUser(subjectId);
                if (Objects.nonNull(user) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    var kcUserId = user.getId();
                    var session = request.getSession(true);
                    session.setAttribute("userId", kcUserId);

                    var authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (VerificationException e) {
                throw new SessionAuthenticationException("Failed to verify token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
