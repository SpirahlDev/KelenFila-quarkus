package org.spirahldev.kelenFila.app.IOmodel.output;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.enums.PersonType;
import org.spirahldev.kelenFila.domain.model.Account;

public record GetOneDataOutput(
    Long id,
    String login,
    PersonType personType,
    ProfileOutput profile,
    PersonOutput person,
    String avatar,
    LocalDateTime updatedAt
){
    public static GetOneDataOutput fromAccount(Account account) {
        if (account == null) {
            return null;
        }
        
        // Mapper le profil
        ProfileOutput profileOutput = ProfileOutput.from(account.getProfile());
        // Mapper la personne
        PersonOutput personOutput = PersonOutput.from(account.getPerson());
        
        return new GetOneDataOutput(
            account.getId(),
            account.getLogin(),
            account.getPersonType(),
            profileOutput,
            personOutput,
            account.getAvatar(),
            account.getUpdatedAt()
        );
    }
}
