package org.spirahldev.kelenFila.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participant")
public class Participant {
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    
    @Id
    @Column(name = "account_id")
    private Long accountId;
    
    @Id
    @Column(name = "auction_id")
    private Long auctionId;

    @OneToOne
    @JoinColumn(name = "participant_role_id",nullable = false)
    private ParticipantRole participantRole;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "has_join_auction")
    private Boolean hasJoinAuction;

    @Column(name = "has_actived_notification")
    private Boolean hasActivedNotification;
}
