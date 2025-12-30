package com.smartcity.ai.authservice.repository;


import com.smartcity.ai.authservice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
