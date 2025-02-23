package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @Column(name = "profile_code", length = 45)
    private String profileCode;

    @Column(name = "profile_name", length = 255)
    private String profileName;

    @Column(length = 255)
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}