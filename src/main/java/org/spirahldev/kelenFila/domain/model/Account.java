package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.enums.PersonType;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
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

    @OneToOne
    @JoinColumn(name = "profile_id",nullable = false)
    private ProfileEntity profile;

    @OneToOne
    @JoinColumn(name = "person_id",nullable = false)
    private Person person;

    @Column(length = 455)
    private String avatar;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at",nullable = true)
    private LocalDateTime deleteAt;

    @Column(name="verified_at")
    private LocalDateTime verifiedAt;

    public LocalDateTime getVerificationDate() {
        return verifiedAt;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public String getLogin() {
        return login;
    }

    public ProfileEntity getProfile(){
        return profile;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getSuspensionDate() {
        return suspendedAt;
    }



    public void setSuspendedAt(LocalDateTime suspendedAt) {
        this.suspendedAt = suspendedAt;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    
}