package com.smartcity.ai.authservice.repository;


import com.smartcity.ai.authservice.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
