package org.spirahldev.kelenFila.app.IOmodel.input;


import org.spirahldev.kelenFila.domain.enums.PersonType;

public record AccountDataInput(
    String login,                   // VARCHAR(160)
    String password,                // VARCHAR(220)
    PersonType personType,              
    String avatar
) {}