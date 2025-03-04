package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.spirahldev.kelenFila.app.IOmodel.input.PersonDataInput;
import org.spirahldev.kelenFila.common.constants.AppStatusCode;
import org.spirahldev.kelenFila.domain.exceptions.BusinessException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.ws.rs.core.Response;

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

    @OneToOne
    @JoinColumn(name="country_id")
    private CountryEntity country;

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

    @Column(name="birth_date")
    private LocalDate birthDate;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
    

    // public Person(PersonDataInput personData){
    //     this.firstname=personData.firstName();
    //     this.lastname=personData.lastName();
    //     this.phoneNumber=personData.phone();
    //     this.email=personData.email();
    //     this.residenceCounty=personData.country();
    //     this.residenceCity=personData.city();
    //     this.address=personData.address();
    //     this.birthDate=personData.birthDate();
    // }

    public CountryEntity getResidenceCounty() {
        return country;
    }

    public void setResidenceCountry(CountryEntity country) {
        this.country = country;
    }

    public String getResidenceCity() {
        return residenceCity;
    }

    public void setResidenceCity(String residenceCity) {
        this.residenceCity = residenceCity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Person from(PersonDataInput personData){
        Person person=new Person();
        
        CountryEntity country=CountryEntity.findById(personData.country());

        if(country==null){
            throw new BusinessException(AppStatusCode.VALIDATION_ERROR,Response.Status.BAD_REQUEST);
        }
        
        person.setResidenceCountry(country);
        person.setFirstname(personData.firstName());
        person.setLastname(personData.lastName());
        person.setPhoneNumber(personData.phone());
        person.setEmail(personData.email());
        person.setResidenceCity(personData.city());
        person.setAddress(personData.address());
        person.setBirthDate(personData.birthDate());

        return person;
    }
}
