package com.smartcity.ai.trafficservice.security;

import com.smartcity.ai.trafficservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        // Vérifier si l'en-tête Authorization existe et commence par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.substring(7);
            String username = jwtService.getUsernameFromToken(jwt);

            // Vérifier si le token est valide et qu'il n'y a pas déjà d'authentification
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Vérifier si le token n'est pas expiré
                if (!jwtService.isTokenExpired(jwt)) {
                    // Extraire les rôles du token
                    List<GrantedAuthority> authorities = jwtService.getRolesFromToken(jwt);
                    
                    // Créer l'authentification avec les rôles
                    UsernamePasswordAuthenticationToken authenticationToken = 
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    // Mettre l'authentification dans le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    
                    System.out.println("✅ JWT authentifié: " + username + " avec rôles: " + authorities);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du décodage JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
