package org.spirahldev.kelenFila.app.IOmodel.input;
import jakarta.validation.constraints.*;

public record ClientRegisterInput(
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    String name,

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

    @NotBlank(message = "La région est obligatoire")
    @Size(max = 100)
    String state,

    @NotBlank(message = "Le pays est obligatoire")
    @Size(max = 100)
    String country,

    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^[0-9]{5}$", message = "Format de code postal invalide")
    String postalCode
) {}