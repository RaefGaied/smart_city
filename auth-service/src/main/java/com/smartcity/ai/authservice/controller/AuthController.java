package com.smartcity.ai.authservice.controller;


import com.smartcity.ai.authservice.dto.LoginResponse;
import com.smartcity.ai.authservice.dto.UserDto;
import com.smartcity.ai.authservice.entities.AppUser;
import com.smartcity.ai.authservice.repository.AppUserRepository;
import com.smartcity.ai.authservice.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody AuthenticationRequest authenticationRequest){
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           authenticationRequest.getUsername(),
                           authenticationRequest.getPassword())
           );
       }catch (Exception e){
           e.printStackTrace();
       }
        final UserDetails user = userDetailsService.loadUserByUsername(
                authenticationRequest.getUsername()
        );
       
        // Récupérer l'utilisateur complet depuis la base
        AppUser appUser = appUserRepository.findByUsername(authenticationRequest.getUsername());
        
        // Générer le token
        String token = jwtService.generateToken(user);
        
        // Construire la réponse avec le token et les infos utilisateur
        UserDto userDto = UserDto.builder()
                .id(appUser.getId())
                .nom(appUser.getUsername())
                .email(appUser.getMail())
                .role(appUser.getRoles().isEmpty() ? "user" : appUser.getRoles().get(0).getName())
                .build();
        
        return LoginResponse.builder()
                .token(token)
                .user(userDto)
                .build();
    }
}

@Data
class AuthenticationRequest {
    private String username;
    private String password;

}