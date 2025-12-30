package com.smartcity.ai.authservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppUser {
    @Id
    private String id;
    @Column(unique=true)
    private String username;
    private String password;
    private String mail;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
