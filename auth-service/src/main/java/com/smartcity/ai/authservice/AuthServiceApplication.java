package com.smartcity.ai.authservice;

import com.smartcity.ai.authservice.entities.AppRole;
import com.smartcity.ai.authservice.entities.AppUser;
import com.smartcity.ai.authservice.repository.AppRoleRepository;
import com.smartcity.ai.authservice.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    /*  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
   CommandLineRunner runner(AppUserRepository appUserRepository,
                            AppRoleRepository appRoleRepository) {
       return args -> {
           AppRole appRole1 = AppRole.builder().name("ADMIN").build();
           AppRole appRole2 = AppRole.builder().name("USER").build();

           appRoleRepository.save(appRole1);
           appRoleRepository.save(appRole2);

           appUserRepository.save(AppUser.builder()
                   .id(UUID.randomUUID().toString())
                   .username("raef")
                   .mail("raef@gmail.com")
                   .password(passwordEncoder().encode("1234"))
                   .roles(List.of(appRole1, appRole2))
                   .build());

           appUserRepository.save(AppUser.builder()
                   .id(UUID.randomUUID().toString())
                   .username("meriem")
                   .mail("meriem@gmail.com")
                   .password(passwordEncoder().encode("1234"))
                   .roles(List.of(appRole2))
                   .build());
       };

   }*/

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
