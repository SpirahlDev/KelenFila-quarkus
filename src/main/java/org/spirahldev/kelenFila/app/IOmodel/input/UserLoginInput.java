package org.spirahldev.kelenFila.app.IOmodel.input;

import jakarta.validation.constraints.NotBlank;

public record UserLoginInput(
    @NotBlank
    String login,
    @NotBlank
    String password
) {}
