package org.spirahldev.kelenFila.app.IOmodel.input;
import java.time.LocalDate;

import org.spirahldev.kelenFila.domain.enums.PersonType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.*;


public record NaturalPersonRegisterInput(
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    String lastname, 
    
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    String firstname,

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    String email,

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    String password,

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Format de téléphone invalide")
    String phone,

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255)
    String address,

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100)
    String city,

    @NotNull(message = "L'identifiant du pays est obligatoire")
    int country,

    @NotNull(message = "La date de naissance est obligatoire")
    LocalDate birthDate, 

    String avatar
) {
    @JsonIgnore
    public PersonDataInput getPersonData() {
        // LocalDate birthDate = LocalDate.parse(this.birthDate);
        
        return new PersonDataInput(
            this.firstname,
            this.lastname, 
            this.email,
            this.phone,
            this.address,
            this.city,
            this.country,
            birthDate,
            this.avatar
        );
    }
    @JsonIgnore
    public AccountDataInput getAccountData() {
        return new AccountDataInput(
            this.email,
            this.password,
            PersonType.NATURAL,
            this.avatar
        );
    }
}