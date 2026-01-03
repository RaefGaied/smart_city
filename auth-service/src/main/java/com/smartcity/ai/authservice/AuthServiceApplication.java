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

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(AppUserRepository appUserRepository,
                            AppRoleRepository appRoleRepository,
                            PasswordEncoder passwordEncoder) {
       return args -> {
           // Créer les rôles
           AppRole appRole1 = AppRole.builder().name("ADMIN").build();
           AppRole appRole2 = AppRole.builder().name("USER").build();

           appRoleRepository.save(appRole1);
           appRoleRepository.save(appRole2);

           // Créer un utilisateur ADMIN
           appUserRepository.save(AppUser.builder()
                   .id(UUID.randomUUID().toString())
                   .username("admin")
                   .mail("admin@smartcity.ai")
                   .password(passwordEncoder.encode("admin123"))
                   .roles(List.of(appRole1, appRole2))
                   .build());

           // Créer un utilisateur USER
           appUserRepository.save(AppUser.builder()
                   .id(UUID.randomUUID().toString())
                   .username("user")
                   .mail("user@smartcity.ai")
                   .password(passwordEncoder.encode("user123"))
                   .roles(List.of(appRole2))
                   .build());
           
           System.out.println("✅ Comptes pré-créés : admin/admin123 (ADMIN+USER), user/user123 (USER)");
       };
   }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
