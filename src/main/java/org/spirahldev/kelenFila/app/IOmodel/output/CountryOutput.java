package org.spirahldev.kelenFila.app.IOmodel.output;

import org.spirahldev.kelenFila.domain.model.CountryEntity;

public record CountryOutput(
    Integer id,
    String countryName,
    String telephoneCode,
    String countryCode
) {
    public static CountryOutput from(CountryEntity country) {
        if (country == null) {
            return null;
        }
        return new CountryOutput(
            country.id,
            country.getCountryName(),
            country.getTelephoneCode(),
            country.getCountryCode()
        );
    }
}