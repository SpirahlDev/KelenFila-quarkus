package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String lastname;

    @Column(length = 150)
    private String firstname;

    @Column(name = "phonenumber", length = 25)
    private String phoneNumber;

    @Column(length = 200)
    private String email;

    @Column(name = "residence_county")
    private Integer residenceCounty;

    @Column(name = "residence_city", length = 45)
    private String residenceCity;

    @Column(length = 100)
    private String address;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "identity_card", length = 400)
    private String identityCard;

    @Column(name = "is_identity_card_checked")
    private Boolean isIdentityCardChecked;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at",nullable = true)
    private LocalDateTime deletedAt;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
    
}
