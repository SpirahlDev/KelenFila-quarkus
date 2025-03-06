package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participant")
@IdClass(ParticipantId.class)
public class Participant {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "account_id")  
    private Account account;

    @Id
    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToOne
    @JoinColumn(name = "participant_role_id",nullable = false)
    private ParticipantRole participantRole;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "has_join_auction")
    private Boolean hasJoinAuction;

    @Column(name = "has_actived_notification")
    private Boolean hasActivedNotification;
}
