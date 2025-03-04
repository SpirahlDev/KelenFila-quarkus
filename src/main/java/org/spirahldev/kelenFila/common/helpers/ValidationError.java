package org.spirahldev.kelenFila.common.helpers;

public record ValidationError(
    String field,
    String message
) {}
