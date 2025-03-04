package org.spirahldev.kelenFila.app.IOmodel.output;

import java.time.LocalDate;

import org.spirahldev.kelenFila.domain.model.Person;

public record PersonOutput(
    Long id,
    String lastname,
    String firstname,
    String phoneNumber,
    String email,
    String residenceCity,
    String address,
    LocalDate birthDate,
    CountryOutput residenceCountry
) {
     public static PersonOutput from(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonOutput(
            person.getId(),
            person.getLastname(),
            person.getFirstname(),
            person.getPhoneNumber(),
            person.getEmail(),
            person.getResidenceCity(),
            person.getAddress(),
            person.getBirthDate(),
            CountryOutput.from(person.getResidenceCounty())
        );
    }
}
