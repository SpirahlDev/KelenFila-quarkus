package org.spirahldev.kelenFila.app.IOmodel.output;

import org.spirahldev.kelenFila.domain.model.ProfileEntity;

public record ProfileOutput(
    Integer id,
    String profileCode,
    String profileName,
    String description,
    Boolean isActive
) {
    public static ProfileOutput from(ProfileEntity profile) {
        if (profile == null) {
            return null;
        }
        return new ProfileOutput(
            profile.id,
            profile.getProfileCode(),
            profile.getProfileName(),
            profile.getDescription(),
            profile.getIsActive()
        );
    }
}