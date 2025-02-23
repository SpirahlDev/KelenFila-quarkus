package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.enums.PersonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 160)
    private String login;

    @Column(length = 220)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "suspended_at")
    private LocalDateTime suspendedAt;

    @Column(name = "remember_token", length = 160)
    private String rememberToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type")
    private PersonType personType;

    @Column(name = "profile_id")
    private Integer profileId;

    @OneToOne
    @JoinColumn(name = "person_id",nullable = false)
    private Person person;

    @Column(length = 455)
    private String avatar;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at",nullable = true)
    private LocalDateTime deleteAt;

    // Getters & Setters
}