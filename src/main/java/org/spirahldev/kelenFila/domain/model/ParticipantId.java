package org.spirahldev.kelenFila.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantId implements Serializable {
    private Long account;
    private Long auction;
    
    public ParticipantId() {
    }
    
    public ParticipantId(Long account, Long auction) {
        this.account = account;
        this.auction = auction;
    }
    
    // Getters and Setters
    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Long getAuction() {
        return auction;
    }

    public void setAuction(Long auction) {
        this.auction = auction;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantId that = (ParticipantId) o;
        return Objects.equals(account, that.account) &&
               Objects.equals(auction, that.auction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, auction);
    }
}